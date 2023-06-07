package com.longterm.artschools.ui.components.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.PointsRepository
import com.longterm.artschools.domain.models.points.Point
import com.longterm.artschools.ui.core.onFailureLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapVm(
    private val pointsRepository: PointsRepository
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        viewModelScope.launch {
            pointsRepository.getPoints()
                .onSuccess { points ->
                    _state.update {
                        State.Data(points.map {
                            SchoolPoint(
                                it,
                                false
                            )
                        })
                    }
                }
                .onFailureLog(TAG)
        }
    }

    fun onPointClicked(point: SchoolPoint) {
        pointsRepository.savePoint(point.point)
        _state.update {
            if (it is State.Data) {
                it.copy(points = it.points.map { p ->
                    if (p == point) {
                        p.copy(selected = true)
                    } else p.copy(selected = false)
                }, showPoint = point.point)
            } else State.Loading
        }
    }

    fun onPointShown() {
        _state.update {
            if (it is State.Data) {
                it.copy(showPoint = null)
            } else State.Loading
        }
    }

    fun clearSelection() {
        _state.update {
            if (it is State.Data) {
                it.copy(points = it.points.map { p ->
                    p.copy(selected = false)
                })
            } else State.Loading
        }
    }

    sealed interface State {
        object Loading : State
        data class Data(val points: List<SchoolPoint>, val showPoint: Point? = null) : State
    }

    data class SchoolPoint(
        val point: Point,
        val selected: Boolean
    )

    companion object {
        private const val TAG = "MapVm"
    }
}