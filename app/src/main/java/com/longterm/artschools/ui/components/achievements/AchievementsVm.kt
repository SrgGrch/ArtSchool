package com.longterm.artschools.ui.components.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class AchievementsVm(
    private val userRepository: UserRepository
) : ViewModel() {

    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        loadData()
    }

    fun retry() {
        _state.update {
            State.Loading
        }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            userRepository.getUser()
                .onSuccess { user ->
                    _state.update {
                        val totalExp = user.level?.totalExp ?: return@update State.Error
                        val currentLevel = user.level.currentLevel ?: return@update State.Error
                        State.Data(
                            totalExp.roundToInt(),
                            currentLevel.level,
                            (1..5000).random(),
                            (5000..800000).random(),
                            remainingToLevelUp = currentLevel.exp,
                            currentLevel.description
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        State.Error
                    }
                }
        }
    }


    sealed interface State {
        data class Data(
            val score: Int,
            val currentLevel: Int,
            val ratingPosition: Int,
            val userCount: Int,
            val remainingToLevelUp: Int,
            val status: String,
            val levelCount: Int = 10
        ) : State

        object Loading : State
        object Error : State
    }
}