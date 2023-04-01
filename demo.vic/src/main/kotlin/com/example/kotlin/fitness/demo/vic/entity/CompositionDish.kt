package com.example.kotlin.fitness.demo.vic.entity


import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "composition_dish_fitness")
class CompositionDish {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCreate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null
    var title: String? = null
    var dish // reference to dish
            : UUID? = null

    @OneToOne(fetch = FetchType.EAGER) //cascade = CascadeType.ALL,
    //@JoinColumn(insertable = false, updatable = false)
    var product: Product? = null
    var weight = 0.0

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        title: String?,
        dish: UUID?,
        product: Product?,
        weight: Double
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.title = title
        this.dish = dish
        this.product = product
        this.weight = weight
    }
}
