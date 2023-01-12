package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.beans.MyException
import com.applicuisine.appli_cuisine_server.dto.PasswordDTO
import com.applicuisine.appli_cuisine_server.entities.UserEntity
import com.applicuisine.appli_cuisine_server.repositories.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class UserService(val userRepository: UserRepository) {

    fun createUserWithEmailAndPassWord(user: UserEntity){
        if(verifyLogin(user)){
            if(!userRepository.existsUserEntityByMail(user.mail!!)){
                userRepository.save(user)
            }else{
                throw MyException("Cette adresse email est déjà utilisée", MyException.ERROR_MAIL)
            }
        }else{
            throw MyException("Un problème a été rencontré, veuillez réessayer", MyException.ERROR_GENERAL_WELCOME)
        }
    }

    fun connectUser(userToConnect:UserEntity, httpSession: HttpSession):Boolean{
        var firstConnexion = false
        if(verifyLogin(userToConnect)){
            val userOptional = userRepository.findUserEntityByMailAndPassword(userToConnect.mail!!,userToConnect.password!!)
            if(userOptional.isPresent){
                val user = userOptional.get()
                if(user.sessionId.isNullOrEmpty()){
                    println("sessionId is null")
                    firstConnexion = true
                }
                user.sessionId = httpSession.id
                userRepository.save(user)
                return firstConnexion
            }else{
                throw MyException("La combinaison email/Mot de passe n'est pas la bonne", MyException.ERROR_GENERAL_WELCOME)
            }
        }else{
            throw MyException("Un problème a été rencontré, veuillez réessayer", MyException.ERROR_GENERAL_WELCOME)
        }
    }

    fun updateUser(sessionId: String, userToUpdate: UserEntity){
        println(sessionId)
        val actualUser = getActualUser(sessionId)
        actualUser.dateOfBirth = userToUpdate.dateOfBirth
        actualUser.sex = userToUpdate.sex
        actualUser.budget = userToUpdate.budget
        actualUser.cuisineLevel = userToUpdate.cuisineLevel
        userRepository.save(actualUser)
    }

    fun getActualUser(sessionId: String): UserEntity {
        val actualUser = userRepository.findUserEntityBySessionId(sessionId)
        if(actualUser.isPresent){
            return actualUser.get()
        }else{
            throw MyException("Un problème a été rencontré, veuillez réessayer", MyException.ERROR_GENERAL_WELCOME)
        }
    }

    private fun isMailValid(email :String):Boolean{
        val regexMail = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
        return regexMail.matcher(email).matches()
    }

    private fun isPasswordValid(password :String):Boolean{
        val regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        return regexPassword.matcher(password).matches()
    }

    private fun verifyLogin(userToVerify: UserEntity):Boolean {
        if(!userToVerify.mail.isNullOrBlank() && !userToVerify.password.isNullOrBlank()){
            if(isMailValid(userToVerify.mail!!)) {
                if(isPasswordValid(userToVerify.password!!)){
                        return true
                }else{
                    throw MyException("Le mot de passe doit être au minimum de 8 caractères et posséder une lettre et un chiffre", MyException.ERROR_PASSWORD)
                }
            }else {
                throw MyException("Votre adresse mail n'est pas valide", MyException.ERROR_PASSWORD)
            }
        }else {
            return false
        }
    }

    fun modifyPassword(sessionId: String, passwordDTO: PasswordDTO):Boolean{
        var successful = false
        val oldPassword = passwordDTO.oldPassword
        val newPassword = passwordDTO.newPassword
        if(!oldPassword.isNullOrBlank() && !newPassword.isNullOrBlank()) {
            if (isPasswordValid(oldPassword)) {
                if (isPasswordValid(newPassword)) {
                    val optionalUser = userRepository.findUserEntityBySessionId(sessionId)
                    if (optionalUser.isPresent) {
                        val user = optionalUser.get()
                        if (user.password.equals(oldPassword)) {
                            user.password = newPassword
                            userRepository.save(user)
                            successful = true
                            return successful
                        } else {
                            throw MyException("Le mot de passe donné ne correspond pas à votre mot de passe", MyException.ERROR_OLD_PASSWORD)
                        }
                    } else {
                        throw Exception("User not found")
                    }
                } else {
                    throw MyException(
                        "Le mot de passe doit être au minimum de 8 caractères et posséder une lettre et un chiffre",
                        MyException.ERROR_NEW_PASSWORD
                    )
                }
            } else {
                throw MyException(
                    "Le mot de passe doit être au minimum de 8 caractères et posséder une lettre et un chiffre",
                    MyException.ERROR_OLD_PASSWORD
                )
            }
        }else{
            successful = false
            return successful
        }
    }
}