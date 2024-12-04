package com.thesua7.drinkable.screen

sealed class PredictionEvent {
    data class Predict(val inputs: FloatArray) : PredictionEvent()
}
