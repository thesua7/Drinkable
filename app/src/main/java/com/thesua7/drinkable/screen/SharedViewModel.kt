package com.thesua7.drinkable.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesua7.drinkable.repository.PredictionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: PredictionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PredictionUiState>(PredictionUiState.Idle)
    val uiState: StateFlow<PredictionUiState> = _uiState.asStateFlow()

    fun onEvent(event: PredictionEvent) {
        when (event) {
            is PredictionEvent.Predict -> handlePrediction(event.inputs)
        }
    }

    private fun handlePrediction(inputs: FloatArray) {
        _uiState.value = PredictionUiState.Loading
        viewModelScope.launch {
            try {
                val (prediction, confidence) = repository.predict(inputs)
                _uiState.value = PredictionUiState.Success(prediction, confidence)
            } catch (e: Exception) {
                _uiState.value = PredictionUiState.Error("Prediction failed: ${e.message}")
            }
        }
    }

    fun clear() {
        _uiState.value = PredictionUiState.Idle
    }
}
