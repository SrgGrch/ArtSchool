package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.UserRepository

class RegisterUseCase(
    private val userRepository: UserRepository
) {
    private var name: String? = null
    private var age: Int? = null
    private var photoUrl: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var repeatPassword: String

    fun supplyPreferences() = Unit // todo
    fun supplyTargets() = Unit // todo

    fun supplyUserInfo(
        name: String,
        age: Int?,
        photoUrl: String?
    ) {
        this.name = name
        this.age = age
        this.photoUrl = photoUrl
    }

    fun supplyCredentials(
        email: String,
        password: String,
        repeatPassword: String
    ) {
        this.email = email
        this.password = password
        this.repeatPassword = repeatPassword
    }

    suspend fun register(): Result<Unit> {
        return userRepository.register(
            email,
            password,
            repeatPassword,
            age,
            name,
            photoUrl
        )
    }
}