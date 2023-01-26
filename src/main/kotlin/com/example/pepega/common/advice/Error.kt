package com.example.pepega.common.advice

sealed interface Error

sealed class IOError : Error, RuntimeException {

    constructor(message: String, throwable: Throwable): super(message, throwable)
    constructor(message: String): super(message)
    constructor(): super()
}

class UserExistExceptionCustom : IOError()
class UserNotFoundExceptionCustom : IOError()
class PasswordNotMatchExceptionCustom : IOError()
