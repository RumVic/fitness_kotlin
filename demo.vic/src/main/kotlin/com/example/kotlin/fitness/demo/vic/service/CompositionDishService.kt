package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.CompositionDishBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.CompositionDish
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputComDishDTO
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.service.api.ICompositionDishService
import com.example.kotlin.fitness.demo.vic.service.api.IProductService
import com.example.kotlin.fitness.demo.vic.storage.ICompositionDishStorage
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CompositionDishService : ICompositionDishService {
    @Autowired
    private val storage: ICompositionDishStorage? = null

    @Autowired
    private val service: IProductService? = null
    @Transactional
    override fun create(list: List<InputComDishDTO?>?, idDish: UUID?): List<CompositionDish?>? {
        val compositionDishes: MutableList<CompositionDish?> = ArrayList()
        for (inputComDishDTO in list!!) {
            val product = service!!.read(inputComDishDTO!!.product!!.id)
            val idCD = UUID.randomUUID()
            val compositionDish = storage!!.save(
                create()
                    .setId(idCD)
                    .setDtCreate(Clock.systemUTC().millis())
                    .setDtUpdate(Clock.systemUTC().millis())
                    .setTitle(inputComDishDTO.title)
                    .setDish(idDish)
                    .setProduct(product)
                    .setWeight(inputComDishDTO.weight)
                    .build()
            )
            compositionDishes.add(read(idCD))
        }
        return compositionDishes
    }

    override fun create(dto: InputComDishDTO, header: String?): CompositionDish? {
        return null
    }

    override fun read(id: UUID?): CompositionDish? {
        return storage!!.findById(id!!).orElseThrow()
    }

    override fun get(pageable: Pageable?): OutPage<*>? {
        return null
    }

    override fun update(item: List<InputComDishDTO?>?, idDish: UUID?): List<CompositionDish?>? {
        val list: MutableList<CompositionDish?> = ArrayList()
        for (dto in item!!) {
            val uuid = UUID.randomUUID()
            val created = storage!!.save(
                create()
                    .setId(uuid)
                    .setDtCreate(Clock.systemUTC().millis())
                    .setDtUpdate(Clock.systemUTC().millis())
                    .setTitle(dto!!.title)
                    .setDish(idDish)
                    .setProduct(dto.product)
                    .setWeight(dto.weight)
                    .build()
            )
            list.add(storage.findById(uuid).orElseThrow())
        }
        return list
    }

    @Throws(LockException::class)
    override fun update(id: UUID?, dtUpdate: Long?, item: InputComDishDTO, header: String?): CompositionDish? {
        return null
    }

    @Transactional
    override fun delete(compositionDish: List<CompositionDish?>?) {
        for (beingDeleted in compositionDish!!) {
            storage!!.deleteById(beingDeleted!!.id!!)
        }
    }

    @Transactional
    override fun delete(compositionDish: CompositionDish) {
    }
}