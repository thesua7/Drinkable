package com.thesua7.drinkable.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PredictionForm(
    modifier: Modifier = Modifier, onPredict: (FloatArray) -> Unit
) {

    val inputFields = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    val inputLabels = listOf(
        "pH",
        "Hardness",
        "Solids",
        "Chloramines",
        "Sulfate",
        "Conductivity",
        "Organic Carbon",
        "Trihalomethanes",
        "Turbidity"
    )

    val inputDescriptions = listOf(
        "Numeric value representing the acidity or alkalinity of the water, typical range: 0.0 to 14.0",
        "Numeric value in mg/L, indicating the concentration of calcium and magnesium ions, typical range: 47 to 323 mg/L.",
        "Numeric value in ppm, representing the total dissolved solids, typical range: 320 to 61227 ppm.",
        "Numeric value in ppm, indicating the concentration of chloramines, typical range: 0.35 to 13 ppm.",
        "Numeric value in ppm, representing the sulfate concentration, typical range: 129 to 481 ppm.",
        "Numeric value in μS/cm, representing the electrical conductivity, typical range: 181 to 753 μS/cm.",
        "Numeric value in ppm, representing the total organic carbon, typical range: 2 to 28 ppm.",
        "Numeric value in μg/L, representing the concentration of trihalomethanes, typical range: 0.50 to 124 μg/L.",
        "Numeric value in NTU, indicating the cloudiness or haziness of water, typical range: 1.45 to 6.74 NTU."
    )

    val errors = remember {
        mutableStateListOf<String?>(null, null, null, null, null, null, null, null, null)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        inputLabels.forEachIndexed { index, label ->
            Column {
                OutlinedTextField(
                    value = inputFields[index],
                    onValueChange = {
                        inputFields[index] = it
                        errors[index] = null // Clear error on input change
                    },
                    label = { Text(label) },
                    placeholder = {
                        Text(
                            text = inputDescriptions[index],
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal
                    ),
                    isError = errors[index] != null
                )
                if (errors[index] != null) {
                    Text(
                        text = errors[index]!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            ), onClick = {
                val inputs = inputFields.mapIndexed { index, value ->
                    value.toFloatOrNull() ?: run {
                        errors[index] = "Invalid number"
                        return@mapIndexed null
                    }
                }

                if (inputs.all { it != null }) {
                    onPredict(inputs.filterNotNull().toFloatArray())
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Predict")
        }
    }
}
