package com.example.kotlin.fitness.demo.vic.idto

import com.example.kotlin.fitness.demo.vic.util.EStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class InputUserByAdmin {
    var mail: @NotBlank @Pattern(
        regexp = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
        message = "Email invalid"
    ) String? = null
    var nick: @NotNull String? = null
    var password: @NotNull String? = null
    var role: @NotNull String? = null
    var status: EStatus? = null

    constructor() {}
    constructor(
        mail: String?,
        nick: String?,
        password: String?,
        role: String?,
        status: EStatus?
    ) {
        this.mail = mail
        this.nick = nick
        this.password = password
        this.role = role
        this.status = status
    }
}