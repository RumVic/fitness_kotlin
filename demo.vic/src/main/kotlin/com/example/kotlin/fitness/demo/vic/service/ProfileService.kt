package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.ProfileBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.Profile
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputProfileDTO
import com.example.kotlin.fitness.demo.vic.mappers.ProfileMapper
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputProfileDTO
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService
import com.example.kotlin.fitness.demo.vic.service.api.IProfileService
import com.example.kotlin.fitness.demo.vic.service.api.IUserService
import com.example.kotlin.fitness.demo.vic.storage.IProfileStorage
import com.example.kotlin.fitness.demo.vic.util.EGender
import com.example.kotlin.fitness.demo.vic.util.ELifestyle
import com.example.kotlin.fitness.demo.vic.util.EntityType
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class ProfileService : IProfileService {
    private val CREATED = "New user's profile was created"

    @Autowired
    private val storage: IProfileStorage? = null

    @Autowired
    private val userService: IUserService? = null
    private val auditService: IAuditService? = null
    private val profileMapper: ProfileMapper? = null
    @Transactional
    override fun create(dto: InputProfileDTO, header: String?): Profile {
        validate(dto)
        val currentUserProfile = userService!!.extractCurrentUserProfile(header)
        val mail = SecurityContextHolder.getContext().authentication.name
        val user = userService.loadCurrentUserByLogin(mail)
        val profile = storage!!.save(
            create()
                .setId(UUID.randomUUID())
                .setDtCreate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setUser(currentUserProfile)
                .setHeight(dto.height)
                .setWeight(dto.weight)
                .setBirthday(dto.birthday)
                .setGender(dto.sex)
                .setLifestyle(dto.activity_type)
                .setTargetWeight(dto.target)
                .build()
        )
        auditService!!.create(
            user,
            EntityType.PROFILE,
            CREATED,
            profile.id.toString()
        )
        return profile
    }

    override fun readById(id: UUID): OutputProfileDTO {
        val profile = storage!!.findById(id).orElseThrow()!!
        return profileMapper!!.map(profile)
    }

    override fun read(id: UUID?): Profile {
        return storage!!.findById(id!!).orElseThrow()!!
    }

    override fun get(pageable: Pageable?): OutPage<*>? {
        return null
    }

    @Transactional
    @Throws(LockException::class)
    override fun update(id: UUID?, dtUpdate: Long?, item: InputProfileDTO, header: String?): Profile {
        return null
    }

    override fun delete(profile: Profile) {}
    fun validate(input: InputProfileDTO) {
        check(input.weight >= 30) { "Min value of weight is 30 kg" }
        check(!(input.height < 100 || input.height > 300)) { "Check you Height" }
        check(!(input.target < 30 || input.target > 200)) { "Check your target" }
        check(
            !(input.sex != EGender.MALE
                    && input.sex != EGender.FEMALE
                    && input.sex != EGender.THEY)
        ) { "Check your SEX" }
        check(
            !(input.activity_type != ELifestyle.ACTIVE
                    && input.activity_type != ELifestyle.PASSIVE
                    && input.activity_type != ELifestyle.COMBINE)
        ) { "Check type of lifestyle" }
    }
}