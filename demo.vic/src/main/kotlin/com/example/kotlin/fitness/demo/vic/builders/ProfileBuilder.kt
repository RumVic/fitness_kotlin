package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.Profile
import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.util.EGender
import com.example.kotlin.fitness.demo.vic.util.ELifestyle
import java.util.*

class ProfileBuilder private constructor() {
    private var id: UUID? = null
    private var dtCreate: Long? = null
    private var dtUpdate: Long? = null
    private var user: User? = null
    private var height = 0.0
    private var weight = 0.0
    private var birthday: Date? = null
    private var gender: EGender? = null
    private var lifestyle: ELifestyle? = null
    private var targetWeight = 0.0
    fun setId(id: UUID?): ProfileBuilder {
        this.id = id
        return this
    }

    fun setDtCreate(dtCreate: Long?): ProfileBuilder {
        this.dtCreate = dtCreate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): ProfileBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setUser(user: User?): ProfileBuilder {
        this.user = user
        return this
    }

    fun setHeight(height: Double): ProfileBuilder {
        this.height = height
        return this
    }

    fun setWeight(weight: Double): ProfileBuilder {
        this.weight = weight
        return this
    }

    fun setBirthday(birthday: Date?): ProfileBuilder {
        this.birthday = birthday
        return this
    }

    fun setGender(gender: EGender?): ProfileBuilder {
        this.gender = gender
        return this
    }

    fun setLifestyle(lifestyle: ELifestyle?): ProfileBuilder {
        this.lifestyle = lifestyle
        return this
    }

    fun setTargetWeight(targetWeight: Double): ProfileBuilder {
        this.targetWeight = targetWeight
        return this
    }

    fun build(): Profile {
        return Profile(id, dtCreate, dtUpdate, user, height, weight, birthday, gender, lifestyle, targetWeight)
    }

    companion object {
        fun create(): ProfileBuilder {
            return ProfileBuilder()
        }
    }
}