package com.example.kotlin.fitness.demo.vic.service;

import com.example.kotlin.fitness.demo.vic.builders.ProductBuilder;
import com.example.kotlin.fitness.demo.vic.entity.Product;
import com.example.kotlin.fitness.demo.vic.entity.User;
import com.example.kotlin.fitness.demo.vic.entity.api.IProductStorage;
import com.example.kotlin.fitness.demo.vic.exception.LockException;
import com.example.kotlin.fitness.demo.vic.idto.InputProductDTO;
import com.example.kotlin.fitness.demo.vic.mappers.ProductMapper;
import com.example.kotlin.fitness.demo.vic.odto.OutPage;
import com.example.kotlin.fitness.demo.vic.odto.OutputProductDTO;
import com.example.kotlin.fitness.demo.vic.security.filter.JwtUtil;
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService;
import com.example.kotlin.fitness.demo.vic.service.api.IProductService;
import com.example.kotlin.fitness.demo.vic.service.api.IUserService;
import com.example.kotlin.fitness.demo.vic.util.EntityType;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final String CREATED = "Line in food journal was created";
    private final String UPDATED = "Line in food journal was updated";

    private final String EDITED = "Line already edited by somebody else";

    private final String LOCK = "Editing forbidden";

    @Autowired
    private final IProductStorage storage;
    @Autowired
    private final IUserService userService;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final IAuditService auditService;
    @Autowired
    private final ProductMapper productMapper;


    @Override
    @Transactional
    public Product create(InputProductDTO idto, String header) {

        validate(idto);
        String login = userService.extractCurrentToken(header);
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadCurrentUserByLogin(mail);


        Product product = storage.save(ProductBuilder
                .create()
                .setId(UUID.randomUUID())
                .setDtCreate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setTitle(idto.getTitle())
                .setWeight(idto.getWeight())
                .setCalories(idto.getCalories())
                .setProteins(idto.getProteins())
                .setFats(idto.getFats())
                .setCarbohydrates(idto.getCarbohydrates())
                .setCreatedByRole(login)
                .build());

        auditService.create(
                user,
                EntityType.PRODUCT,
                CREATED,
                product.getId().toString()
        );

        return product;
    }

    public Product read(UUID uuid) {
        return storage.findById(uuid).orElseThrow();
    }

    @Override
    public OutPage<OutputProductDTO> get(Pageable pag) {
        Page<Product> pageOfProduct = storage.findAll(pag);
        return productMapper.map(pageOfProduct);
    }

    @Override
    @Transactional
    public Product update(UUID id, Long dtUpdate, InputProductDTO idto, String header) throws LockException {

        validate(idto);
        Product readed = storage.findById(id).orElseThrow();

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadCurrentUserByLogin(mail);

        if (!readed.getCreatedByRole().equals(user.getLogin())) {
            if (!user.getRole().equals("ROLE_ADMIN")) {
                throw new LockException(LOCK);
            }
        }

        String login = userService.extractCurrentToken(header);

       /* if (readed == null) {
            throw new IllegalArgumentException("Product wasn't found");
        }*/


        if (!readed.getDtUpdate().equals(dtUpdate)) {
            throw new OptimisticLockException(EDITED);
        }

        Product productUpdate = storage.save(ProductBuilder
                .create()
                .setId(readed.getId())
                .setDtCreate(readed.getDtCreate())
                .setDtUpdate(Clock.systemUTC().millis())
                .setTitle(idto.getTitle())
                .setWeight(idto.getWeight())
                .setCalories(idto.getCalories())
                .setProteins(idto.getProteins())
                .setFats(idto.getFats())
                .setCarbohydrates(idto.getCarbohydrates())
                .setCreatedByRole(login)
                .build());


        auditService.create(user, EntityType.PRODUCT, UPDATED, productUpdate.getId().toString());


        return productUpdate;
    }

    @Override
    public void delete(Product product) {
    }

    public void validate(InputProductDTO idto) {
        if (idto == null) {
            throw new IllegalStateException("You didn't pass the product");
        }
        if (idto.getTitle() == null) {
            throw new IllegalStateException("You didn't pass the Title");
        }
        if (idto.getFats() < 0 || idto.getFats() == 0) {
            throw new IllegalStateException("You didn't pass the value of Fats");
        }
        if (idto.getCarbohydrates() < 0 || idto.getCarbohydrates() == 0) {
            throw new IllegalStateException("You didn't pass the value of Carbohydrates");
        }
        if (idto.getProteins()<0 || idto.getProteins() == 0){
            throw new IllegalStateException("You didn't pass the value of Proteins");
        }
        if (idto.getCalories()<0 || idto.getCalories() ==0){
            throw new IllegalStateException("You didn't pass the value of Calories");
        }
        if (idto.getWeight()<0 || idto.getWeight()==0){
            throw new IllegalStateException("You didn't pass the value of Weight");
        }
    }
}