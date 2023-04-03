package com.example.kotlin.fitness.demo.vic.odto

import com.example.kotlin.fitness.demo.vic.util.EGender
import com.example.kotlin.fitness.demo.vic.util.ELifestyle
import java.util.*

class OutputProfileDTO {
    var id: UUID? = null
    var dtCreate: Long? = null
    var dtUpdate: Long? = null
    var height = 0.0
    var weight = 0.0
    var birthday: Date? = null
    var gender: EGender? = null
    var lifestyle: ELifestyle? = null
    var targetWeight = 0.0
    var user: OutputUserDTO? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        height: Double,
        weight: Double,
        birthday: Date?,
        gender: EGender?,
        lifestyle: ELifestyle?,
        targetWeight: Double,
        user: OutputUserDTO?
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.height = height
        this.weight = weight
        this.birthday = birthday
        this.gender = gender
        this.lifestyle = lifestyle
        this.targetWeight = targetWeight
        this.user = user
    }
}