package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.api.VkApi
import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.models.User

class VkAuthUseCase(
    private val userRepository: UserRepository,
    private val vkApi: VkApi
) {
    suspend fun execute(
        code: String,
        clientId: String,
        clientSecret: String
    ): Result<User> {
        val getTokenResult = vkApi.getAccessToken(
            code,
            clientId,
            clientSecret
        )

        return if (getTokenResult.isSuccess) {
            val r = getTokenResult.getOrThrow()
            val result = userRepository.register(r.accessToken, r.email)

            if (result.isSuccess) {
                userRepository.updateUser()
            } else {
                Result.failure(result.exceptionOrNull()!!)
            }
        } else Result.failure(getTokenResult.exceptionOrNull()!!)
    }
}