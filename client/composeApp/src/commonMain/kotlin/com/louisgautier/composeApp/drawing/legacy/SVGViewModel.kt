package com.louisgautier.composeApp.drawing.legacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SVGViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    sealed class CharacterUiState {
        object Loading : CharacterUiState()
        data class Success(val graphic: Graphic) : CharacterUiState()
        data class Error(val message: String) : CharacterUiState()
    }

    private var _state: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Loading)
    val state: StateFlow<CharacterUiState> = _state

    init {
//        fetchSVG('⺀')
//        fetchSVG('㐆')
//        fetchSVG('寸')
    }

    fun fetchSVG(code: Int) {
        viewModelScope.launch {
            characterRepository.getSVG(code)
                .onSuccess { data ->
                    _state.update { CharacterUiState.Success(data) }
                }
                .onFailure { msg ->
                    _state.update { CharacterUiState.Error(msg.message ?: "Unknow Error") }
                }
        }
    }
}