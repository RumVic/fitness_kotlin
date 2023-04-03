package com.example.kotlin.fitness.demo.vic.mappers

import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputUserDTO
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun onceMap(user: User): OutputUserDTO {
        val dto = OutputUserDTO()
        dto.id = user.id
        dto.dtCrate = user.dtCrate
        dto.dtUpdate = user.dtUpdate
        dto.login = user.login
        dto.role = user.role
        dto.status = user.status
        return dto
    }

    fun map(userPage: Page<User>): OutPage<OutputUserDTO?> {
        val dto: OutPage<OutputUserDTO?> = OutPage<Any?>()
        dto.number = userPage.number
        dto.size = userPage.size
        dto.totalPages = userPage.totalPages
        dto.totalElements = userPage.totalElements.toInt()
        dto.isFirst = userPage.isFirst
        dto.numberOfElements = userPage.numberOfElements
        dto.isLast = userPage.isLast
        val dtoList: MutableList<OutputUserDTO?> = ArrayList()
        for (user in userPage.content) {
            val userDTO = transfer(user)
            dtoList.add(userDTO)
        }
        dto.content = dtoList
        return dto
    }

    fun transfer(user: User): OutputUserDTO {
        val dto = OutputUserDTO()
        dto.id = user.id
        dto.dtCrate = user.dtCrate
        dto.dtUpdate = user.dtUpdate
        dto.role = user.role
        dto.login = user.login
        dto.status = user.status
        return dto
    }
}