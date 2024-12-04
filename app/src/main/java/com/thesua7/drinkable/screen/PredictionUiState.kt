package com.thesua7.drinkable.screen

sealed class PredictionUiState {
    data object Idle : PredictionUiState()
    data object Loading : PredictionUiState()
    data class Success(val prediction: String, val confidence: Float) : PredictionUiState()
    data class Error(val message: String) : PredictionUiState()
}
