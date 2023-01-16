package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.beans.MyException
import com.applicuisine.appli_cuisine_server.dto.PasswordDTO
import com.applicuisine.appli_cuisine_server.entities.UserEntity
import com.applicuisine.appli_cuisine_server.repositories.UserRepository
import com.applicuisine.appli_cuisine_server.utils.*
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
                throw MyException(ERROR_MESSAGE_MAIL_USED, MyException.ERROR_MAIL)
            }
        }else{
            throw MyException(ERROR_GENERAL_MESSAGE, MyException.ERROR_GENERAL_WELCOME)
        }
    }

    fun connectUser(userToConnect:UserEntity, sessionId: String):Boolean{
        if(verifyLogin(userToConnect)){
            val user = find(userToConnect.mail!!, userToConnect.password!!)
            val firstConnexion = user.isFirstConnexion()
            saveNewSessionIdToUser(user, sessionId)
            return firstConnexion
        }else{
            throw MyException(ERROR_MESSAGE_PASSWORD_MAIL_MATCH, MyException.ERROR_GENERAL_WELCOME)
        }
    }

    fun find(mail:String, password:String):UserEntity{
        val userOptional = userRepository.findUserEntityByMailAndPassword(mail,password)
        if(userOptional.isPresent) {
            return userOptional.get()
        }else{
            throw Exception(ERROR_MESSAGE_USER_NOT_FOUND)
        }
    }

    fun saveNewSessionIdToUser(user: UserEntity, sessionId: String){
        user.sessionId = sessionId
        userRepository.save(user)
    }

    fun updateUser(sessionId: String, userToUpdate: UserEntity){
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
            throw MyException(ERROR_GENERAL_MESSAGE, MyException.ERROR_GENERAL_WELCOME)
        }
    }

    private fun verifyLogin(userToVerify: UserEntity):Boolean {
        return if(!userToVerify.mail.isNullOrBlank() && !userToVerify.password.isNullOrBlank()){
            if(userToVerify.mail!!.isMailValid()) {
                if(userToVerify.password!!.isPasswordValid()){
                    true
                }else{
                    throw MyException(ERROR_MESSAGE_INVALID_PASSWORD, MyException.ERROR_PASSWORD)
                }
            }else {
                throw MyException(ERROR_MESSAGE_INVALID_MAIL, MyException.ERROR_PASSWORD)
            }
        }else {
            false
        }
    }

    fun modifyPassword(sessionId: String, passwordDTO: PasswordDTO):Boolean{
        var successful = false
        val oldPassword = passwordDTO.oldPassword.trim()
        val newPassword = passwordDTO.newPassword.trim()
        if(!oldPassword.isNullOrBlank() && !newPassword.isNullOrBlank()) {
            if (oldPassword.isPasswordValid()) {
                if (newPassword.isPasswordValid()) {
                    val user = find(sessionId)
                    if (user.password.equals(oldPassword)) {
                        user.password = newPassword
                        userRepository.save(user)
                        successful = true
                        return successful
                    } else {
                        throw MyException(ERROR_MESSAGE_PASSWORD_MATCH, MyException.ERROR_OLD_PASSWORD)
                    }
                } else {
                    throw MyException(
                        ERROR_MESSAGE_INVALID_PASSWORD,
                        MyException.ERROR_NEW_PASSWORD
                    )
                }
            } else {
                throw MyException(
                    ERROR_MESSAGE_INVALID_PASSWORD,
                    MyException.ERROR_OLD_PASSWORD
                )
            }
        }else{
            successful = false
            return successful
        }
    }

    fun find(sessionId: String):UserEntity{
        val optionalUser = userRepository.findUserEntityBySessionId(sessionId)
        if (optionalUser.isPresent) {
            return optionalUser.get()
        }else {
            throw Exception(ERROR_MESSAGE_USER_NOT_FOUND)
        }
    }
}