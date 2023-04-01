package com.example.kotlin.fitness.demo.vic.storage

import com.example.kotlin.fitness.demo.vic.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserStorage : JpaRepository<User?, UUID?> {
    fun findByLogin(login: String?): User?
    override fun findAll(pageable: Pageable): Page<User?>
    fun findByActivationCode(code: String?): User?
}