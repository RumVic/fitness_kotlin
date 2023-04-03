package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.DiaryFoodBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.DiaryFood
import com.example.kotlin.fitness.demo.vic.entity.Dish
import com.example.kotlin.fitness.demo.vic.entity.Product
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputDiaryFoodDTO
import com.example.kotlin.fitness.demo.vic.mappers.DiaryFoodMapper
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputDiaryFoodDTO
import com.example.kotlin.fitness.demo.vic.service.api.*
import com.example.kotlin.fitness.demo.vic.storage.IDiaryFoodStorage
import com.example.kotlin.fitness.demo.vic.util.EntityType
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
class DiaryFoodService : IDiaryFoodService {
    private val CREATED = "Line in food journal was created"
    private val LOCK = "You can't create the line "
    private val STARTAPP = 1674752530161L

    @Autowired
    private val userService: IUserService? = null

    @Autowired
    private val storage: IDiaryFoodStorage? = null

    @Autowired
    private val serviceDish: IDishService? = null

    @Autowired
    private val productService: IProductService? = null
    private val auditService: IAuditService? = null
    private val profileService: IProfileService? = null
    private val diaryFoodMapper: DiaryFoodMapper? = null
    @Transactional
    @Throws(LockException::class)
    override fun createWithParam(dto: InputDiaryFoodDTO?, header: String?, uuid: UUID?): DiaryFood? {
        validateDiary(dto)
        val readedDish: Dish? = null
        var readedProduct: Product? = null
        val uid = userService!!.extractCurrentUUID(header)
        val mail = SecurityContextHolder.getContext().authentication.name
        val user = userService.loadCurrentUserByLogin(mail)
        val profile = profileService!!.read(uuid)
        if (dto!!.dish == null) {
            val readProduct = productService!!.read(dto.product.id)
            readedProduct = readProduct
        }
        if (dto.product == null) {
            var readDish = serviceDish!!.read(dto.dish.id)
            readDish = readDish
        }

        //Product readedProduct = productService.read(dto.getProduct().getId());
        if (user!!.id != profile.user!!.id) {
            throw LockException(LOCK)
        }
        val diaryFood = storage!!.save(
            create()
                .setId(UUID.randomUUID())
                .setDtCreate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setDtSupply(dto.dtSupply)
                .setDish(readedDish)
                .setWeightDish(dto.weightDish)
                .setProduct(readedProduct)
                .setWeightProduct(dto.weightProduct)
                .setProfile(uuid)
                .build()
        )
        auditService!!.create(
            user,
            EntityType.DIARY_FOOD,
            CREATED,
            diaryFood.id.toString()
        )
        return diaryFood
    }

    override fun read(id: UUID?): DiaryFood? {
        return storage!!.findById(id!!).orElseThrow()
    }

    override fun get(pageable: Pageable?, id: UUID?): OutPage<OutputDiaryFoodDTO?>? {
        val page = storage!!.findAllByProfile(pageable, id)
        return diaryFoodMapper!!.map(page)
    }

    override fun getListOfLine(id: UUID?): List<DiaryFood?>? {
        return null //storage.findByProfile(id);
    }

    override fun get(pageable: Pageable?): OutPage<*>? {
        return null
    }

    @Transactional
    @Throws(LockException::class)
    override fun update(id: UUID?, dtUpdate: Long?, item: InputDiaryFoodDTO, header: String?): DiaryFood? {
        return null
    }

    override fun delete(diaryFood: DiaryFood) {}
    @Transactional
    override fun create(dto: InputDiaryFoodDTO, header: String?): DiaryFood? {
        return null
    }

    fun validateDiary(dto: InputDiaryFoodDTO?) {
        check(!(dto!!.dish == null && dto.product == null)) { "You need to pass Product or Dish" }
        check(!(dto.weightProduct == 0.0 && dto.weightDish == 0.0)) { "You need to pass weight of food" }
        check(dto.dtSupply >= STARTAPP) { "State valid value of meal time" }
    }
}