package com.example.kotlin.fitness.demo.vic.idto

import com.example.kotlin.fitness.demo.vic.serializator.DateDeSerializer
import com.example.kotlin.fitness.demo.vic.util.EGender
import com.example.kotlin.fitness.demo.vic.util.ELifestyle
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.*

class InputProfileDTO {
    var height = 0.0
        private set
    var weight = 0.0
        private set

    @JsonDeserialize(using = DateDeSerializer::class)
    var birthday: Date? = null
        private set
    var target = 0.0
        private set
    var activity_type: ELifestyle? = null
        private set
    var sex: EGender? = null
        private set

    constructor() {}
    constructor(
        height: Double,
        weight: Double,
        dt_birthday: Date?,
        target: Double,
        activity_type: ELifestyle?,
        sex: EGender?
    ) {
        this.height = height
        this.weight = weight
        birthday = dt_birthday
        this.target = target
        this.activity_type = activity_type
        this.sex = sex
    }
}