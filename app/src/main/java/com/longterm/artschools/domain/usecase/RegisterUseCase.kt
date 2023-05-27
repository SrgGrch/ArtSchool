package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.models.User
import com.longterm.artschools.ui.core.onSuccessMap
import com.longterm.artschools.ui.core.onSuccessMapResult

class RegisterUseCase(
    private val userRepository: UserRepository,
    private val vkAuthUseCase: VkAuthUseCase
) {
    private var name: String? = null
    private var age: Int? = null
    private var photoUrl: String? = null
    private var preferencesCodes: List<String>? = null
    private var targetsCodes: List<String>? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var repeatPassword: String

    fun supplyPreferences(codes: List<String>) {
        preferencesCodes = codes
    }

    fun supplyTargets(codes: List<String>) {
        targetsCodes = codes
    }

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
            preferencesCodes,
            targetsCodes
        ).onSuccessMap {
            userRepository.updateUser()
        } // todo send other data
    }

    suspend fun registerViaVk(
        code: String,
        clientId: String,
        clientSecret: String
    ): Result<User> {
        return vkAuthUseCase.execute(
            code,
            clientId,
            clientSecret,
        ).onSuccessMapResult { user ->
            userRepository.sendAdditionalData(preferencesCodes, targetsCodes).map { user }
        }
    }
}