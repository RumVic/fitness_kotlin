package com.example.kotlin.fitness.demo.vic.odto

import com.example.kotlin.fitness.demo.vic.util.EntityType

class OutputAuditDTO {
    var user: OutputUserDTO? = null
    var text: String? = null
    var type: EntityType? = null
    var uid: String? = null

    constructor() {}
    constructor(user: OutputUserDTO?, text: String?, type: EntityType?, uid: String?) {
        this.user = user
        this.text = text
        this.type = type
        this.uid = uid
    }
}