package com.thesua7.drinkable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PredictionScreen(viewModel: SharedViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimationLoader(
            modifier = Modifier.fillMaxSize() // Fill the entire screen
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween, // Distribute space between elements
            horizontalAlignment = Alignment.Start
        ) {
            when (uiState) {
                is PredictionUiState.Success -> {
                    val prediction = (uiState as PredictionUiState.Success).prediction
                    val confidence =
                        "%.2f".format((uiState as PredictionUiState.Success).confidence * 100)
                    ContentContainer("Prediction: $prediction", "Confidence: $confidence%") {
                        viewModel.clear()
                    }
                }

                is PredictionUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is PredictionUiState.Error -> {
                    ContentContainer(
                        (uiState as PredictionUiState.Error).message, onClearClick = null
                    )
                }

                else -> {
                    DefaultContainer(
                        "Our advanced Water Potability Prediction System evaluates water quality by analyzing nine critical parameters: pH, Hardness, Solids, Chloramines, Sulfate, Conductivity, Organic Carbon, Trihalomethanes, and Turbidity. By leveraging these inputs, the system accurately determines whether the water is safe for drinking. This solution combines scientific rigor with intelligent algorithms to provide reliable, actionable insights, empowering individuals and organizations to make informed decisions about water safety."
                    )

                }
            }
        }
    }
}

@Composable
private fun DefaultContainer(
    text: String,
    modifier: Modifier = Modifier,

    ) {
    Box{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "$text",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,

                )

            Spacer(modifier = Modifier.size(40.dp))
            Text(
                text = buildAnnotatedString {
                    append("press ")
                    withStyle(
                        SpanStyle(
                            fontSize = 30.sp, fontWeight = FontWeight.Bold
                        )
                    ) { // Larger size for "+"
                        append("+")
                    }
                    append(" to find water quality.")
                }, fontSize = 25.sp, color = MaterialTheme.colorScheme.onBackground
            )

        }
    }
}


@Composable
private fun ContentContainer(
    text: String,
    secondText: String? = null,
    modifier: Modifier = Modifier,
    onClearClick: (() -> Unit)?
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.background
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            if (onClearClick != null) {
                Text(text = "Clear",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            onClearClick()

                        })
            }


            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "$text",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,

                )

            Spacer(modifier = Modifier.height(10.dp))
            if (secondText != null) {
                Text(
                    text = "$secondText", fontSize = 20.sp,

                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

