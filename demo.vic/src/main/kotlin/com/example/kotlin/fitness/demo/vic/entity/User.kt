package com.example.kotlin.fitness.demo.vic.entity

import com.example.kotlin.fitness.demo.vic.util.EStatus
import jakarta.persistence.*
import lombok.Data
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.accessibility.AccessibleState

@Entity
@Data
@Table(name = "user_fitness")
class User : UserDetails {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCrate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null
    private var username: String? = null

    @Column(unique = true)
    var login: String? = null
    var activationCode //new
            : String? = null
    private var password: String? = null
    var role: String? = null

    //private Collection<GrantedAuthority> role;
    @Enumerated(value = EnumType.STRING)
    var status: EStatus? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCrate: Long?,
        dtUpdate: Long?,
        username: String?,
        login: String?,
        activationCode: String?,
        password: String?,
        role: String?,
        status: EStatus?
    ) {
        this.id = id
        this.dtCrate = dtCrate
        this.dtUpdate = dtUpdate
        this.username = username
        this.login = login
        this.activationCode = activationCode
        this.password = password
        this.role = role
        this.status = status
    }

    override fun getUsername(): String {                                                       // TODO WARNING
        return login!!
    }

    fun setUsername(name: String?) {
        username = name
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return setOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return if (status == AccessibleState.ACTIVE) {
            true
        } else false
    }
}