package com.longterm.artschools.ui.components.onboarding.userInfo

import androidx.lifecycle.ViewModel
import com.longterm.artschools.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OnboardingUserInfoViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow(State())

    fun onNameChanged(name: String) {
        _state.update {
            it.copy(name = name)
        }
    }

    fun onAgeChanged(age: String) {
        _state.update {
            when (val intAge = age.toIntOrNull()) {
                null -> it.copy(age = null, ageError = "Неверный возраст")
                in Int.MIN_VALUE..0 -> it.copy(age = intAge, ageError = "Неверный возраст")
                in 110..Int.MAX_VALUE -> it.copy(age = intAge, ageError = "Неверный возраст")
                else -> it.copy(age = intAge, ageError = null)
            }
        }
    }

    fun navigateNext() {
        registerUseCase.supplyUserInfo(
            state.value.name,
            state.value.age,
            null//photo url todo
        )
        _state.update {
            it.copy(navigateNext = true)
        }
    }

    fun onNavigateNext() {
        _state.update {
            it.copy(navigateNext = false)
        }
    }

    fun skip() {
        _state.update {
            it.copy(skip = true)
        }
    }

    fun onSkip() {
        _state.update {
            it.copy(skip = false)
        }
    }

    data class State(
        val name: String = "",
        val age: Int? = null,
        val ageError: String? = null,
        val navigateNext: Boolean = false,
        val skip: Boolean = false
    ) {
        val nextButtonEnabled: Boolean
            get() = name.isNotBlank() && age != null && ageError == null
    }
}