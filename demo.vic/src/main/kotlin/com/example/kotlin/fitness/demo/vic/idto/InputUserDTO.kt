package com.example.kotlin.fitness.demo.vic.idto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class InputUserDTO {
    var mail: @NotBlank @Pattern(
        regexp = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
        message = "Email invalid"
    ) String? = null
        private set
    var nick: @NotBlank String? = null
        private set
    var password: String? = null
        private set

    constructor() {}
    constructor(mail: String?, nick: String?, password: String?) {
        this.mail = mail
        this.nick = nick
        this.password = password
    }
}