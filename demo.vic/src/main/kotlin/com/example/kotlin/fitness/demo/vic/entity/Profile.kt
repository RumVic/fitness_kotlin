package com.example.kotlin.fitness.demo.vic.entity

import com.example.kotlin.fitness.demo.vic.util.EGender
import com.example.kotlin.fitness.demo.vic.util.ELifestyle
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "profile_fitness")
class Profile {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCreate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null

    @OneToOne
    var user: User? = null
    var height = 0.0
    var weight = 0.0
    var birthday: Date? = null

    @Enumerated(value = EnumType.STRING)
    var gender: EGender? = null

    @Enumerated(value = EnumType.STRING)
    var lifestyle: ELifestyle? = null
    var targetWeight = 0.0

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        user: User?,
        height: Double,
        weight: Double,
        birthday: Date?,
        gender: EGender?,
        lifestyle: ELifestyle?,
        targetWeight: Double
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.user = user
        this.height = height
        this.weight = weight
        this.birthday = birthday
        this.gender = gender
        this.lifestyle = lifestyle
        this.targetWeight = targetWeight
    }
}