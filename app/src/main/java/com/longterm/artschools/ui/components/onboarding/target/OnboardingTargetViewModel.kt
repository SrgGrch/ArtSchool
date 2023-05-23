package com.longterm.artschools.ui.components.onboarding.target

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OnboardingTargetViewModel : ViewModel() {

    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Data(getChips()))

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
        //todo send interests
        _state.update {
            State.NextPage(it.chips)
        }

    }

    fun skip() {
        _state.update {
            State.NotNow(it.chips)
        }
    }

    private fun getChips() = listOf(
        State.Chip("Просто так"),
        State.Chip("Собираюсь поступать в МШИ"),
        State.Chip("Учусь в МШИ"),
        State.Chip("Я родитель"),
        State.Chip("Для саморазвития")
    )

    fun nextPageInvoked() {
        _state.update {
            State.Data(it.chips)
        }
    }

    sealed class State {
        abstract val chips: List<Chip>

        data class Data(override val chips: List<Chip>) : State()
        data class NextPage(override val chips: List<Chip>) : State()
        data class NotNow(override val chips: List<Chip>) : State()

        data class Chip(
            val text: String,
            val isSelected: Boolean = false,
        )
    }
}