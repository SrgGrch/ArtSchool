package com.longterm.artschools.ui.components.auth

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import kotlin.time.Duration.Companion.seconds

class AuthViewModel : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state : MutableStateFlow<State> = MutableStateFlow(State.Initial)

    fun close() {
        _state.value = State.Close
    }

    fun emailChanged(value: TextFieldValue) {
        _state.update {
            if (value.text.isBlank()) {
                State.Initial
            } else {
                if (it is State.InternalAuth) {
                    it.copy(email = value.text, isDoneButtonEnabled = isEmailValid(value.text) && it.password.isNotBlank())
                } else {
                    State.InternalAuth(value.text, "")
                }
            }
        }
    }

    fun passwordChanged(value: TextFieldValue) {
        _state.update {
            (it as State.InternalAuth).copy(
                password = value.text,
                isDoneButtonEnabled = isEmailValid(it.email) && value.text.isNotBlank()
            )
        }
    }

    fun loginViaVk() {
        _state.value = State.Vk
    }

    fun doneClicked() {

    }

    fun doNotRememberPasswordClicked() {

    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    sealed interface State {
        object Initial : State
        data class InternalAuth(val email: String, val password: String, val isDoneButtonEnabled: Boolean = false) : State
        object Vk : State
        object Close : State
    }
}