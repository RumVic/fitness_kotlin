package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.util.EStatus
import java.util.*

class UserBuilder private constructor() {
    private var id: UUID? = null
    private var dtCrate: Long? = null
    private var dtUpdate: Long? = null
    private var username: String? = null
    private var login: String? = null
    private var password: String? = null
    private var role: String? = null
    private var status: EStatus? = null
    private var activationCode: String? = null
    fun setId(id: UUID?): UserBuilder {
        this.id = id
        return this
    }

    fun setDtCrate(dtCrate: Long?): UserBuilder {
        this.dtCrate = dtCrate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): UserBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setUsername(username: String?): UserBuilder {
        this.username = username
        return this
    }

    fun setLogin(login: String?): UserBuilder {
        this.login = login
        return this
    }

    fun setPassword(password: String?): UserBuilder {
        this.password = password
        return this
    }

    fun setRole(role: String?): UserBuilder {
        this.role = role
        return this
    }

    fun setStatus(status: EStatus?): UserBuilder {
        this.status = status
        return this
    }

    fun setActivationCode(activationCode: String?): UserBuilder {
        this.activationCode = activationCode
        return this
    }

    fun build(): User {
        return User(id, dtCrate, dtUpdate, username, login, activationCode, password, role, status)
    }

    companion object {
        fun create(): UserBuilder {
            return UserBuilder()
        }
    }
}