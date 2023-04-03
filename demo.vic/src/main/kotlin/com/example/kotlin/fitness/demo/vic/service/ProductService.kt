package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.ProductBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.Product
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputProductDTO
import com.example.kotlin.fitness.demo.vic.mappers.ProductMapper
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputProductDTO
import com.example.kotlin.fitness.demo.vic.security.filter.JwtUtil
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService
import com.example.kotlin.fitness.demo.vic.service.api.IProductService
import com.example.kotlin.fitness.demo.vic.service.api.IUserService
import com.example.kotlin.fitness.demo.vic.storage.IProductStorage
import com.example.kotlin.fitness.demo.vic.util.EntityType
import jakarta.persistence.OptimisticLockException
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class ProductService : IProductService {
    private val CREATED = "Line in food journal was created"
    private val UPDATED = "Line in food journal was updated"
    private val EDITED = "Line already edited by somebody else"
    private val LOCK = "Editing forbidden"

    @Autowired
    private val storage: IProductStorage? = null

    @Autowired
    private val userService: IUserService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null

    @Autowired
    private val auditService: IAuditService? = null

    @Autowired
    private val productMapper: ProductMapper? = null
    @Transactional
    override fun create(idto: InputProductDTO, header: String?): Product? {
        validate(idto)
        val login = userService!!.extractCurrentToken(header)
        val mail = SecurityContextHolder.getContext().authentication.name
        val user = userService.loadCurrentUserByLogin(mail)
        val product = storage!!.save(
            create()
                .setId(UUID.randomUUID())
                .setDtCreate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setTitle(idto.title)
                .setWeight(idto.weight)
                .setCalories(idto.calories)
                .setProteins(idto.proteins)
                .setFats(idto.fats)
                .setCarbohydrates(idto.carbohydrates)
                .setCreatedByRole(login)
                .build()
        )
        auditService!!.create(
            user,
            EntityType.PRODUCT,
            CREATED,
            product.id.toString()
        )
        return product
    }

    override fun read(uuid: UUID?): Product? {
        return storage!!.findById(uuid!!).orElseThrow()
    }

    override fun get(pag: Pageable?): OutPage<OutputProductDTO?>? {
        val pageOfProduct = storage!!.findAll(pag!!)
        return productMapper!!.map(pageOfProduct)
    }

    @Transactional
    @Throws(LockException::class)
    override fun update(id: UUID?, dtUpdate: Long?, idto: InputProductDTO, header: String?): Product? {
        validate(idto)
        val readed = storage!!.findById(id!!).orElseThrow()!!
        val mail = SecurityContextHolder.getContext().authentication.name
        val user = userService!!.loadCurrentUserByLogin(mail)
        if (readed.createdByRole != user!!.login) {
            if (user.role != "ROLE_ADMIN") {
                throw LockException(LOCK)
            }
        }
        val login = userService.extractCurrentToken(header)

        /* if (readed == null) {
            throw new IllegalArgumentException("Product wasn't found");
        }*/if (readed.dtUpdate != dtUpdate) {
            throw OptimisticLockException(EDITED)
        }
        val productUpdate = storage.save(
            create()
                .setId(readed.id)
                .setDtCreate(readed.dtCreate)
                .setDtUpdate(Clock.systemUTC().millis())
                .setTitle(idto.title)
                .setWeight(idto.weight)
                .setCalories(idto.calories)
                .setProteins(idto.proteins)
                .setFats(idto.fats)
                .setCarbohydrates(idto.carbohydrates)
                .setCreatedByRole(login)
                .build()
        )
        auditService!!.create(user, EntityType.PRODUCT, UPDATED, productUpdate.id.toString())
        return productUpdate
    }

    override fun delete(product: Product) {}
    fun validate(idto: InputProductDTO?) {
        checkNotNull(idto) { "You didn't pass the product" }
        checkNotNull(idto.title) { "You didn't pass the Title" }
        check(!(idto.fats < 0 || idto.fats == 0.0)) { "You didn't pass the value of Fats" }
        check(!(idto.carbohydrates < 0 || idto.carbohydrates == 0.0)) { "You didn't pass the value of Carbohydrates" }
        check(!(idto.proteins < 0 || idto.proteins == 0.0)) { "You didn't pass the value of Proteins" }
        check(!(idto.calories < 0 || idto.calories == 0.0)) { "You didn't pass the value of Calories" }
        check(!(idto.weight < 0 || idto.weight == 0.0)) { "You didn't pass the value of Weight" }
    }
}