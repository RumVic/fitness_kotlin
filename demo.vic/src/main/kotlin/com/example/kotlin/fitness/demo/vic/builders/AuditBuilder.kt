package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.Audit
import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.util.EntityType
import java.util.*

class AuditBuilder private constructor() {
    private var id: UUID? = null
    private var dtCreate: Long? = null
    private var dtUpdate: Long? = null
    private var user: User? = null
    private var text: String? = null
    private var type: EntityType? = null
    private var uid: String? = null
    fun setId(id: UUID?): AuditBuilder {
        this.id = id
        return this
    }

    fun setDtCreate(dtCreate: Long?): AuditBuilder {
        this.dtCreate = dtCreate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): AuditBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setUser(user: User?): AuditBuilder {
        this.user = user
        return this
    }

    fun setText(text: String?): AuditBuilder {
        this.text = text
        return this
    }

    fun setType(type: EntityType?): AuditBuilder {
        this.type = type
        return this
    }

    fun setUid(uid: String?): AuditBuilder {
        this.uid = uid
        return this
    }

    fun build(): Audit {
        return Audit(id, dtCreate, dtUpdate, user, text, type, uid)
    }

    companion object {
        fun create(): AuditBuilder {
            return AuditBuilder()
        }
    }
}