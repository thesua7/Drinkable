package com.thesua7.drinkable.model

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context
import android.util.Log
import com.thesua7.drinkable.R
import java.io.File
import java.nio.FloatBuffer



class OnnxConnector(context: Context, ) {

    private val ortEnvironment: OrtEnvironment = OrtEnvironment.getEnvironment()
    private val ortSession: OrtSession

    init {
        val modelFile = createTempFileFromRawResource(context, R.raw.random_forest_model)
        ortSession = ortEnvironment.createSession(modelFile.absolutePath)
    }

    fun predictPotability(inputs: FloatArray): Pair<String, Float> {
        if (inputs.size != 9) throw IllegalArgumentException("Input array must have exactly 9 features.")

        val inputTensor = OnnxTensor.createTensor(
            ortEnvironment,
            FloatBuffer.wrap(inputs),
            longArrayOf(1, 9)
        )

        return try {
            Log.d("WaterPotabilityPredictor", "Starting ONNX model session with input tensor.")

            val results = ortSession.run(mapOf(ortSession.inputNames.first() to inputTensor))
            val resultValue = results[0].value

            Log.d("WaterPotabilityPredictor", "Result type: ${resultValue.javaClass.name}")
            Log.d("WaterPotabilityPredictor", "Raw result: $resultValue")

            if (resultValue is LongArray) {
                val predictedClass = resultValue[0] // Get the first (and only) class index
                val prediction = if (predictedClass == 1L) "Potable" else "Non-potable"
                Log.d("WaterPotabilityPredictor", "Prediction: $prediction")

                prediction to 1.0f // Return a fixed probability of 1.0
            } else {
                throw IllegalStateException("Unsupported output type: ${resultValue.javaClass.name}")
            }
        } catch (e: Exception) {
            Log.e("WaterPotabilityPredictor", "Error during model execution: ${e.message}", e)
            throw e
        } finally {
            inputTensor.close() // Always close input tensor
        }
    }

    fun close() {
        ortSession.close()
        ortEnvironment.close()
    }

    private fun createTempFileFromRawResource(context: Context, resId: Int): File {
        val inputStream = context.resources.openRawResource(resId)
        val tempFile = File.createTempFile("model", ".onnx", context.cacheDir)
        inputStream.use { input -> tempFile.outputStream().use { input.copyTo(it) } }
        return tempFile
    }
}
