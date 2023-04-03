package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.UserBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputUserByAdmin
import com.example.kotlin.fitness.demo.vic.idto.InputUserDTO
import com.example.kotlin.fitness.demo.vic.mappers.UserMapper
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputUserDTO
import com.example.kotlin.fitness.demo.vic.security.filter.JwtUtil
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService
import com.example.kotlin.fitness.demo.vic.service.api.IUserService
import com.example.kotlin.fitness.demo.vic.storage.IUserStorage
import com.example.kotlin.fitness.demo.vic.util.EStatus
import com.example.kotlin.fitness.demo.vic.util.EntityType
import jakarta.persistence.OptimisticLockException
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SignatureException
import java.time.Clock
import java.util.*

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class UserService : IUserService, UserDetailsService {
    private val CREATED = "The User was created"
    private val UPDATED = "The user was updated"
    private val EDITED = "Value user's line was  updated somebody else"
    private val LOCK = "Editing forbidden"
    private val EXISTLOGIN = "The same Email already exist"
    private val passwordEncoder: BCryptPasswordEncoder? = null
    private val jwtUtil: JwtUtil? = null
    private val userStorage: IUserStorage? = null
    private val auditService: IAuditService? = null
    private val userMapper: UserMapper? = null
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByLogin(login: String?): UserDetails? {
        val user = userStorage!!.findByLogin(login)
        if (user == null) {
            UserService.log.error("User not found in the database")
            throw UsernameNotFoundException("User not found in the database")
        } else {
            UserService.log.info("User found in the database:{}", login)
        }
        return user
    }

    @Transactional
    override fun createNewUser(dto: InputUserByAdmin?): UserDetails? {
        validate(dto)
        val user = userStorage!!.save(
            create()
                .setId(UUID.randomUUID())
                .setDtCrate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setUsername(dto!!.nick)
                .setLogin(dto.mail)
                .setPassword(passwordEncoder!!.encode(dto.password))
                .setRole(dto.role)
                .setStatus(dto.status)
                .build()
        )
        auditService!!.create(user, EntityType.USER, CREATED, user.id.toString())
        return user
    }

    override fun readInput(id: UUID?): OutputUserDTO? {
        val user = userStorage!!.findById(id!!).orElseThrow()!!
        return userMapper!!.onceMap(user)
    }

    override fun read(id: UUID?): User? {
        return userStorage!!.findById(id!!).orElseThrow()!!
    }

    override fun get(pageable: Pageable?): OutPage<*> {
        val page = userStorage!!.findAll(
            pageable!!
        )
        return userMapper!!.map(page)
    }

    @Transactional
    @Throws(LockException::class, SignatureException::class)
    override fun update(
        id: UUID?,
        dtUpdate: Long?,
        item: InputUserByAdmin?,
        header: String?
    ): User? {
        validate(item)
        val login = extractCurrentToken(header)
        val mail = SecurityContextHolder.getContext().authentication.name
        val user = loadCurrentUserByLogin(mail)
        val readedUser = read(id)
        check(readedUser!!.login != item!!.mail) { EXISTLOGIN }
        if (readedUser.username != user!!.login && user.role != "ROLE_ADMIN") {
            throw LockException(LOCK)
        }
        if (readedUser.dtUpdate != dtUpdate) {
            throw OptimisticLockException(EDITED)
        }
        val updateUser = userStorage!!.save(
            create()
                .setId(id)
                .setDtCrate(readedUser.dtCrate)
                .setDtUpdate(Clock.systemUTC().millis())
                .setUsername(item.nick)
                .setLogin(item.mail)
                .setPassword(passwordEncoder!!.encode(item.password))
                .setRole(item.role)
                .setStatus(item.status)
                .build()
        )
        auditService!!.create(user, EntityType.USER, UPDATED, updateUser.id.toString())
        return updateUser
    }

    fun validate(dto: InputUserByAdmin?) {
        check(!(dto!!.role != "ROLE_USER" && dto.role != "ROLE_ADMIN")) { "You need pass role properly" }
        check(!(dto.status != EStatus.ACTIVE && dto.status != EStatus.WAITING_ACTIVATION && dto.status != EStatus.DEACTIVATED)) { "You need pass Status properly" }
    }

    @Throws(SignatureException::class)
    override fun getMyInfo(header: String?): OutputUserDTO? {
        val currentLogin = extractCurrentToken(header)
        val user = userStorage!!.findByLogin(currentLogin)
        return userMapper!!.onceMap(user!!)
    }

    @Throws(SignatureException::class)
    override fun extractCurrentToken(authHeader: String?): String? {
        val jwtToken = authHeader!!.substring(7)
        val email = jwtUtil!!.extractUsername(jwtToken)
        val currentUser = loadUserByLogin(email)
        return currentUser!!.username
    }

    @Throws(SignatureException::class)
    override fun extractCurrentUUID(authHeader: String?): UUID? {
        val jwtToken = authHeader!!.substring(7)
        val email = jwtUtil!!.extractUsername(jwtToken)
        val currentUserDetails = loadUserByLogin(email)
        val currentLogin = currentUserDetails!!.username
        val currentUser = userStorage!!.findByLogin(currentLogin)
        return currentUser!!.id
    }

    @Throws(SignatureException::class)
    override fun extractCurrentUserProfile(authHeader: String?): User? {
        val jwtToken = authHeader!!.substring(7)
        val email = jwtUtil!!.extractUsername(jwtToken)
        val currentUserDetails = loadUserByLogin(email)
        val currentLogin = currentUserDetails!!.username
        return userStorage!!.findByLogin(currentLogin)
    }

    override fun loadCurrentUserByLogin(login: String?): User? {
        return userStorage!!.findByLogin(login)
    }

    @Throws(LockException::class)
    override fun update(
        id: UUID?,
        dtUpdate: Long?,
        item: InputUserDTO,
        header: String?
    ): User? {
        return null
    }

    override fun delete(user: User) {}
    override fun create(dto: InputUserDTO, header: String?): User? {
        return null
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return null
    }
}