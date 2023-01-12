package com.applicuisine.appli_cuisine_server.repositories

import com.applicuisine.appli_cuisine_server.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {

    fun existsUserEntityByMail(mail: String):Boolean
    fun findUserEntityByMailAndPassword(mail: String,password:String): Optional<UserEntity>
    fun findUserEntityBySessionId(sessionId : String): Optional<UserEntity>
}