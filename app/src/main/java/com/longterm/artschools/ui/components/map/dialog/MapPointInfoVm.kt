package com.longterm.artschools.ui.components.map.dialog


import androidx.lifecycle.ViewModel
import com.longterm.artschools.data.repository.PointsRepository
import com.longterm.artschools.domain.models.points.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapPointInfoVm(
    private val pointsRepository: PointsRepository
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow(pointsRepository.lastPoint?.let { State.Data(it) } ?: State.Error)

    sealed interface State {
        data class Data(val point: Point) : State
        object Error : State
    }
}