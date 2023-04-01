package com.example.kotlin.fitness.demo.vic.entity


import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "dish_fitness")
class Dish {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCreate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null
    var title: String? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "dish")
    var compositionDishList: List<CompositionDish>? = null

    @Column(name = "create_by_role")
    var createByRole: String? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        title: String?,
        compositionDishList: List<CompositionDish>?,
        createByRole: String?
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.title = title
        this.compositionDishList = compositionDishList
        this.createByRole = createByRole
    }
}
