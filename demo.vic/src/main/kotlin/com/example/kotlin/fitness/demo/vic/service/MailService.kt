package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.UserBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.idto.InputUserDTO
import com.example.kotlin.fitness.demo.vic.mail.MailSender
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService
import com.example.kotlin.fitness.demo.vic.storage.IUserStorage
import com.example.kotlin.fitness.demo.vic.util.EStatus
import com.example.kotlin.fitness.demo.vic.util.EntityType
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.Clock
import java.util.*

@Service
@RequiredArgsConstructor
class MailService : UserDetailsService {
    private val ADDED_IN_DB = "The User was successfully added"
    private val ACTIVATED = "The User was successfully activated"
    private val storage: IUserStorage? = null

    @Autowired
    private val mailSender: MailSender? = null

    @Autowired
    private val auditService: IAuditService? = null
    private val passwordEncoder: BCryptPasswordEncoder? = null
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return null
    }

    fun addUser(user: InputUserDTO): User {
        val createdUser = //create User from [assed json
            create()
                .setId(UUID.randomUUID())
                .setDtCrate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setUsername(user.nick)
                .setLogin(user.mail)
                .setPassword(passwordEncoder!!.encode(user.password))
                .setRole("ROLE_USER")
                .setStatus(EStatus.WAITING_ACTIVATION)
                .setActivationCode(UUID.randomUUID().toString())
                .build()
        val userFromDb = storage!!.findByLogin(user.mail) //find user from DB
        require(userFromDb == null) { "The user with the same login already exist" }
        val savedUser = storage.save(createdUser)
        auditService!!.create(createdUser, EntityType.USER, ADDED_IN_DB, createdUser.id.toString())
        if (user.mail != null) {
            val message = ("Hello. Welcome to Fitness. " +
                    "Please go here to finish registration." +
                    "http://localhost:8080/activation/"
                    + savedUser.activationCode
                    + "/mail/"
                    + user.mail)
            mailSender!!.send(user.mail, "ActivationCode", message)
        }
        return savedUser
    }

    @Throws(IOException::class)
    fun activateUser(code: String?): User {
        val user =
            storage!!.findByActivationCode(code) ?: throw IOException("We are apologize , try to registration again")
        user.activationCode = null
        user.status = EStatus.ACTIVE
        auditService!!.create(user, EntityType.USER, ACTIVATED, user.id.toString())
        return storage.save(user)
    }
}