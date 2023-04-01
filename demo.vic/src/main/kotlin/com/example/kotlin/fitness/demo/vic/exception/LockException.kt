package com.example.kotlin.fitness.demo.vic.exception

class LockException : Exception {
    constructor() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, e: Exception?) : super(message, e) {}
    constructor(e: Exception?) : super(e) {}
}