package com.example.kotlin.fitness.demo.vic.odto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class OutputResponseError(toString: String, s1: String) {
    private val logref: String? = null
    private val message: String? = null
}