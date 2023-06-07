package com.longterm.artschools.ui.components.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileVm(
    private val userRepository: UserRepository
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        loadData()
    }

    fun signOut() {
        userRepository.signOut()
        _state.update {
            State.SignOut
        }
    }

    fun retry() = loadData()

    private fun loadData() = viewModelScope.launch {
        userRepository.updateUser()
            .onSuccess { user ->
                _state.update {
                    State.Data(user)
                }
            }
            .onFailure {
                Log.e("ProfileVm", it.stackTraceToString())
                _state.update {
                    State.Error
                }
            }
    }

    sealed interface State {
        object Loading : State
        object Error : State
        object SignOut : State
        data class Data(val user: User) : State
    }
}