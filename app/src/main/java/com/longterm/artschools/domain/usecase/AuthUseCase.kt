package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.models.User
import com.longterm.artschools.ui.core.onSuccessMap

class AuthUseCase(
    private val userRepository: UserRepository,
    private val vkAuthUseCase: VkAuthUseCase
) {
    suspend fun authorize(email: String, password: String): Result<Unit> {
        return userRepository.authorize(
            email,
            password
        ).onSuccessMap {
            userRepository.updateUser()
        }
    }

    suspend fun authorizeViaVk(
        code: String,
        clientId: String,
        clientSecret: String
    ): Result<User> {
        return vkAuthUseCase.execute(
            code,
            clientId,
            clientSecret,
        )
    }
}