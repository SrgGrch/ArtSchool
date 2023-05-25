package com.longterm.artschools.ui.components.onboarding.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initial())

    fun onEmailChanged(value: String) {
        _state.update {
            if (it is State.InternalRegister) {
                val errorPrompt = if (!isValidEmail(value)) "Неверный email" else null
                val errors = it.errors?.copy(email = errorPrompt) ?: State.InternalRegister.Errors(errorPrompt)

                if (isAllFieldBlank(value, it.password, it.repeatPassword)) {
                    State.Initial(value, false)
                } else {
                    it.copy(
                        email = value,
                        isDoneButtonEnabled = isDoneEnabled(value, it.password, it.repeatPassword),
                        errors = errors
                    )
                }
            } else {
                State.InternalRegister(value)
            }
        }
    }

    fun onPasswordChanged(value: String) {
        _state.update {
            val st = (it as State.InternalRegister)
            val errorPrompt = if (!isValidPassword(value)) "Пароль должен быть длинее 8 символов" else null
            val errors = st.errors?.copy(password = errorPrompt) ?: State.InternalRegister.Errors(errorPrompt)

            st.copy(
                password = value,
                repeatPassword = "",
                isDoneButtonEnabled = isDoneEnabled(it.email, value, it.repeatPassword),
                errors = errors
            )
        }
    }

    fun onRepeatChanged(value: String) {
        _state.update {
            val st = (it as State.InternalRegister)
            val errorPrompt = if (st.password != value) "Пароли не совпадают" else null
            val errors = st.errors?.copy(repeatPassword = errorPrompt) ?: State.InternalRegister.Errors(errorPrompt)


            st.copy(
                repeatPassword = value,
                isDoneButtonEnabled = isDoneEnabled(it.email, it.password, value),
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
        _state.update {
            State.Done(it.email, it.isDoneButtonEnabled)
        }
    }

    private fun isDoneEnabled(email: String, password: String, repeatPassword: String): Boolean {
        return isValidEmail(email)
                && password.isNotBlank()
                && password == repeatPassword
    }

    private fun isAllFieldBlank(email: String, password: String, repeatPassword: String): Boolean {
        return email.isBlank()
                && password.isBlank()
                && repeatPassword.isBlank()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String) = password.length >= 8

    sealed interface State {
        val email: String
        val isDoneButtonEnabled: Boolean

        data class Initial(override val email: String = "", override val isDoneButtonEnabled: Boolean = false) : State

        data class InternalRegister(
            override val email: String,
            val password: String = "",
            val repeatPassword: String = "",
            val errors: Errors? = null,
            override val isDoneButtonEnabled: Boolean = false,
        ) : State {
            data class Errors(
                val email: String? = null,
                val password: String? = null,
                val repeatPassword: String? = null
            )
        }

        data class Vk(
            override val email: String = "",
            override val isDoneButtonEnabled: Boolean = false
        ) : State

        data class Done(
            override val email: String = "",
            override val isDoneButtonEnabled: Boolean = false
        ) : State
    }
}