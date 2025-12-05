package com.louisgautier.composeApp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.Statistics
import com.louisgautier.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration

class HomeViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    data class UIState(
        val isLoading: Boolean = true,
        val lastSession: Session? = null,
        val stats: Statistics = Statistics(
            totalScore = 0,
            averageTime = Duration.ZERO,
            averageDifficulty = null,
            currentDayStreak = 0,
            sessionCount = 0
        ),
    )

    private var _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = sessionRepository.getLastSessions(1)
                .firstOrNull()
            val stats = sessionRepository.getStatistics()
            with(_state.value) {
                _state.value = copy(
                    isLoading = false,
                    lastSession = result,
                    stats = stats,
                )
            }
        }
    }
}

