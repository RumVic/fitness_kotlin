package com.example.kotlin.fitness.demo.vic.service.api

import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputUserByAdmin
import com.example.kotlin.fitness.demo.vic.idto.InputUserDTO
import com.example.kotlin.fitness.demo.vic.odto.OutputUserDTO
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface IUserService : IService<User?, InputUserDTO?, OutputUserDTO?> {
    fun createNewUser(dto: InputUserByAdmin?): UserDetails?
    fun loadUserByLogin(login: String?): UserDetails?
    fun extractCurrentToken(authHeader: String?): String?
    fun extractCurrentUUID(header: String?): UUID?
    fun extractCurrentUserProfile(header: String?): User?
    fun getMyInfo(header: String?): OutputUserDTO?
    fun loadCurrentUserByLogin(login: String?): User?
    fun readInput(id: UUID?): OutputUserDTO?

    @Throws(LockException::class)
    fun update(
        id: UUID?,
        dtUpdate: Long?,
        item: InputUserByAdmin?,
        header: String?
    ): User?
}