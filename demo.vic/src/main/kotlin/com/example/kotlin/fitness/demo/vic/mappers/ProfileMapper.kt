package com.example.kotlin.fitness.demo.vic.mappers

import com.example.kotlin.fitness.demo.vic.entity.Profile
import com.example.kotlin.fitness.demo.vic.odto.OutputProfileDTO
import org.springframework.stereotype.Component

@Component
class ProfileMapper {
    fun map(profiles: Profile): OutputProfileDTO {
        val profileDTO = OutputProfileDTO()
        profileDTO.id = profiles.id
        profileDTO.dtCreate = profiles.dtCreate
        profileDTO.dtUpdate = profiles.dtUpdate
        profileDTO.gender = profiles.gender
        profileDTO.height = profiles.height
        profileDTO.weight = profiles.weight
        profileDTO.birthday = profiles.birthday
        profileDTO.lifestyle = profiles.lifestyle
        profileDTO.targetWeight = profiles.targetWeight
        val userMapper = UserMapper()
        profileDTO.user = userMapper.onceMap(profiles.user)
        return profileDTO
    }
}