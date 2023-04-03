package com.example.kotlin.fitness.demo.vic.service.api

import com.example.kotlin.fitness.demo.vic.entity.Dish
import com.example.kotlin.fitness.demo.vic.idto.InputDishDTO
import com.example.kotlin.fitness.demo.vic.odto.OutputDishDTO

interface IDishService : IService<Dish?, InputDishDTO?, OutputDishDTO?>