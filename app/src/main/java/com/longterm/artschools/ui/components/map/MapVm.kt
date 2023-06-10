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

    private lateinit var pointList: List<SchoolPoint>

    init {
        viewModelScope.launch {
            pointsRepository.getPoints()
                .onSuccess { points ->
                    pointList = points.map {
                        SchoolPoint(
                            it,
                            false
                        )
                    }
                    _state.update {
                        State.Data(
                            pointList,
                            getFilters(),
                        )
                    }
                }
                .onFailureLog(TAG)
        }
    }

    fun onFilterClick(id: Int) {
        _state.update {
            val data = it as? State.Data ?: return@update it

            val filters = data.filters.map { filter ->
                if (filter.id == id) filter.copy(isSelected = !filter.isSelected)
                else filter
            }

            val selectedFilters = filters.filter { it.isSelected }.map { it.id }.toSet()

            data.copy(
                points = pointList.filter {
                    if (selectedFilters.isEmpty()) true
                    else it.point.preferences.intersect(selectedFilters).isNotEmpty()
                },
                filters = filters
            )
        }
    }

    fun onSearchValueChanged(query: String) {
        _state.update {
            (it as? State.Data)?.copy(points = if (query.isBlank()) pointList else pointList.filter { item ->
                item.point.name.contains(query, true) || item.point.description.contains(query, true)
            }, searchQuery = query) ?: it
        }
    }

    private fun getFilters(): List<Filter> {
        return listOf(
            Filter(1, "✍️ Дизайн"),
            Filter(2, "🎭 Театр"),
            Filter(3, "🎨 Живопись"),
            Filter(4, "🩰 Хореография"),
            Filter(5, "🎶 Музыка"),
        )
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
        data class Data(
            val points: List<SchoolPoint>,
            val filters: List<Filter>,
            val showPoint: Point? = null,
            val searchQuery: String = ""
        ) : State
    }

    data class Filter(
        val id: Int,
        val text: String,
        val isSelected: Boolean = false
    )

    data class SchoolPoint(
        val point: Point,
        val selected: Boolean
    )

    companion object {
        private const val TAG = "MapVm"
    }
}