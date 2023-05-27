package com.longterm.artschools.ui.components.onboarding.art

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.OnboardingRepository
import com.longterm.artschools.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingArtViewModel(
    private val registerUseCase: RegisterUseCase,
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Loading())

    init {
        viewModelScope.launch {
            _state.update { state ->
                getChips().getOrNull()?.let { State.Data(it) } ?: State.Error(state.chips)
            }
        }
    }

    fun chipClicked(chip: State.Chip) {
        if (_state.value !is State.Data) return

        _state.update { state ->
            state as State.Data

            state.copy(chips = state.chips.map {
                if (it == chip) chip.copy(isSelected = !it.isSelected)
                else it
            })
        }
    }

    fun nextPage() {
        registerUseCase
            .supplyPreferences(_state.value.chips.map { it.code })

        _state.update {
            State.NextPage(it.chips)
        }

    }

    fun skip() {
        _state.update {
            State.NotNow(it.chips)
        }
    }

    private suspend fun getChips() = onboardingRepository
        .getPreferences()
        .map {
            it.map { target ->
                State.Chip(
                    target.code,
                    target.name
                )
            }
        }

    fun onNextPageInvoked() {
        _state.update {
            State.Data(it.chips)
        }
    }

    fun onSkip() {
        _state.update {
            State.Data(it.chips)
        }
    }

    sealed class State {
        abstract val chips: List<Chip>

        data class Data(override val chips: List<Chip>) : State()
        data class NextPage(override val chips: List<Chip>) : State()
        data class NotNow(override val chips: List<Chip>) : State()

        data class Error(override val chips: List<Chip>) : State()
        data class Loading(override val chips: List<Chip> = listOf()) : State()

        data class Chip(
            val code: String,
            val text: String,
            val isSelected: Boolean = false,
        )
    }
}