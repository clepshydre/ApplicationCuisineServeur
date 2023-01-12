package com.applicuisine.appli_cuisine_server.controller

import com.applicuisine.appli_cuisine_server.beans.MyException
import com.applicuisine.appli_cuisine_server.dto.PasswordDTO
import com.applicuisine.appli_cuisine_server.entities.UserEntity
import com.applicuisine.appli_cuisine_server.services.UserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(val userService: UserService) {

    @GetMapping("/test")
    fun test(response: HttpServletResponse,httpSession: HttpSession ):Any{
        println("/test")
        try{
            val passwordDTO = PasswordDTO("choucroute123","choucroute1234")
            userService.modifyPassword("4DC88668CCA678F328DDD59C992BEB77",passwordDTO)
            return ""
        }catch(e:Exception){
            return e
        }
    }

    @PostMapping("/createUser")
    fun createUser(@RequestBody user :UserEntity, response: HttpServletResponse):Any{
        println("/createUser: $user mail: ${user.mail}")
        try {
            userService.createUserWithEmailAndPassWord(user)
            return ""
        }catch (e:MyException){
            println(e.errorMessage)
            response.status = 500
            return e
        }
    }

    @PostMapping("/connexion")
    fun connectUser(@RequestBody userToConnect: UserEntity, response: HttpServletResponse, httpSession: HttpSession):Any{
        println("/connectUser: $userToConnect mail:${userToConnect.mail} idSession: ${httpSession.id}")
        return try{
            val firstConnexion = userService.connectUser(userToConnect, httpSession)
            println("first connexion: $firstConnexion")
            firstConnexion
        }catch(e:Exception){
            e.printStackTrace()
            response.status = 500
            e
        }
    }

    @PostMapping("/updateUser")
    fun updateUser(@RequestBody user: UserEntity, httpSession: HttpSession, response: HttpServletResponse){
        println("/updateUser: id: ${user.id} mail:${user.mail}")
        try {
            userService.updateUser(httpSession.id, user)
        }catch (e:MyException) {
            println(e.errorMessage)
            response.status = 500
            e
        }
    }

    @GetMapping("/findActualUser")
    fun actualUser(httpSession: HttpSession, response: HttpServletResponse):Any{
        println("/findActualUser")
        return try {
            userService.getActualUser(httpSession.id)
        }catch (e:MyException) {
            println(e.errorMessage)
            response.status = 500
            e
        }
    }

    @PostMapping("/modifyPassword")
    fun modifyPassword(@RequestBody passwordDTO: PasswordDTO, httpSession: HttpSession, response: HttpServletResponse):Any{
        println("/modifyPassword")
        return try {
            return userService.modifyPassword(httpSession.id, passwordDTO)
        }catch (e:MyException) {
            println(e.errorMessage)
            response.status = 500
            e
        }
    }
}