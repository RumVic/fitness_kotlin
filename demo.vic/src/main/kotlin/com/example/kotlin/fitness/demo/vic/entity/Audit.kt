package com.example.kotlin.fitness.demo.vic.entity

import com.example.kotlin.fitness.demo.vic.util.EntityType
import jakarta.persistence.*
import org.springframework.security.core.userdetails.User
import java.util.*

@Entity
@Table(name = "audit_fitness")
class Audit {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCreate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
    var text: String? = null

    @Enumerated(value = EnumType.STRING)
    var type: EntityType? = null
    var uid: String? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        user: User?,
        text: String?,
        type: EntityType?,
        uid: String?
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.user = user
        this.text = text
        this.type = type
        this.uid = uid
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Audit) return false
        val audit = o
        return id == audit.id && dtCreate == audit.dtCreate && dtUpdate == audit.dtUpdate && user == audit.user && text == audit.text && type === audit.type && uid == audit.uid
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            dtCreate,
            dtUpdate,
            user,
            text,
            type,
            uid
        )
    }
}