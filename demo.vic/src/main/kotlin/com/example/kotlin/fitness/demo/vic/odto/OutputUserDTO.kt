package com.example.kotlin.fitness.demo.vic.odto

import com.example.kotlin.fitness.demo.vic.util.EStatus
import java.util.*

class OutputUserDTO {
    var id: UUID? = null
    var dtCrate: Long? = null
    var dtUpdate: Long? = null
    var login: String? = null
    var role: String? = null
    var status: EStatus? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCrate: Long?,
        dtUpdate: Long?,
        login: String?,
        role: String?,
        status: EStatus?
    ) {
        this.id = id
        this.dtCrate = dtCrate
        this.dtUpdate = dtUpdate
        this.login = login
        this.role = role
        this.status = status
    }
}