package com.longterm.artschools.ui.components.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initial())

    fun onEmailChanged(value: String) {
        _state.update {
            if (it is State.InternalAuth) {
                val errorPrompt = if (!isValidEmail(value)) "Неверный email" else null
                val errors = it.errors?.copy(email = errorPrompt) ?: State.InternalAuth.Errors(errorPrompt)

                if (isAllFieldBlank(value, it.password)) {
                    State.Initial(value, false)
                } else {
                    it.copy(
                        email = value,
                        isDoneButtonEnabled = isDoneEnabled(value, it.password),
                        errors = errors
                    )
                }
            } else {
                State.InternalAuth(value)
            }
        }
    }

    fun onPasswordChanged(value: String) {
        _state.update {
            val st = (it as State.InternalAuth)
            val errorPrompt = if (!isValidPassword(value)) "Пароль должен быть длинее 8 символов" else null
            val errors = st.errors?.copy(password = errorPrompt) ?: State.InternalAuth.Errors(errorPrompt)

            st.copy(
                password = value,
                isDoneButtonEnabled = isDoneEnabled(it.email, value),
                errors = errors
            )
        }
    }

    fun onLoginViaVkClicked() {
        _state.update {
            State.Vk(it.email, it.isDoneButtonEnabled)
        }
    }

    fun onDoneClicked() {
        val st = state.value as? State.InternalAuth ?: error("Wrong state")
        viewModelScope.launch {
            authUseCase.authorize(st.email, st.password)
                .onSuccess {
                    _state.update {
                        State.Done(it.email, it.isDoneButtonEnabled)
                    }
                }
                .onFailure {
                    _state.update { state ->
                        state.copyState(showError = true)
                    }
                }.getOrThrow()
        }
    }

    fun onCloseClicked() {
        _state.update {
            State.Close
        }
    }

    fun onDontRememberPasswordClicked() {}

    private fun isDoneEnabled(email: String, password: String): Boolean {
        return isValidEmail(email) && password.isNotBlank()
    }

    private fun isAllFieldBlank(email: String, password: String): Boolean {
        return email.isBlank() && password.isBlank()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String) = password.length >= 8

    sealed interface State {
        val email: String
        val isDoneButtonEnabled: Boolean
        val showError: Boolean

        fun copyState(
            email: String = this.email,
            isDoneButtonEnabled: Boolean = this.isDoneButtonEnabled,
            showError: Boolean = this.showError,
        ): State {
            return when (this) {
                is Done -> copy(email = email, isDoneButtonEnabled = isDoneButtonEnabled, showError = showError)
                is Initial -> copy(email = email, isDoneButtonEnabled = isDoneButtonEnabled, showError = showError)
                is InternalAuth -> copy(email = email, isDoneButtonEnabled = isDoneButtonEnabled, showError = showError)
                is Vk -> copy(email = email, isDoneButtonEnabled = isDoneButtonEnabled, showError = showError)
                is Close -> Close
            }
        }

        data class Initial(
            override val email: String = "",
            override val isDoneButtonEnabled: Boolean = false,
            override val showError: Boolean = false
        ) : State

        data class InternalAuth(
            override val email: String,
            val password: String = "",
            val repeatPassword: String = "",
            val errors: Errors? = null,
            override val isDoneButtonEnabled: Boolean = false,
            override val showError: Boolean = false
        ) : State {
            data class Errors(
                val email: String? = null,
                val password: String? = null,
                val repeatPassword: String? = null
            )
        }

        data class Vk(
            override val email: String = "",
            override val isDoneButtonEnabled: Boolean = false,
            override val showError: Boolean = false
        ) : State

        data class Done(
            override val email: String = "",
            override val isDoneButtonEnabled: Boolean = false,
            override val showError: Boolean = false
        ) : State

        object Close : State {
            override val email: String = ""
            override val isDoneButtonEnabled: Boolean = false
            override val showError: Boolean = false
        }
    }
}