package com.thesua7.drinkable.repository

import com.thesua7.drinkable.model.OnnxConnector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PredictionRepository @Inject constructor(
    private val predictor: OnnxConnector
) {

    fun predict(inputs: FloatArray): Pair<String, Float> {
        return predictor.predictPotability(inputs)
    }

    fun clear(){
        predictor.close()
    }

}
