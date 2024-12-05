package com.thesua7.drinkable.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.thesua7.drinkable.R

@Composable
@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4")
fun PreviewAboutScreen(){
    AboutScreen()
}
@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier.fillMaxSize() // Make the Box take up the entire screen
    ) {
        // Lottie animation taking up the whole screen as background
        LottieAnimationLoader(
            modifier = Modifier.fillMaxSize() // Fill the entire screen
        )

        // Contents placed on top of the animation
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween, // Distribute space between elements
            horizontalAlignment = Alignment.Start // Align all content to the start
        ) {
            Column {
                Spacer(modifier = Modifier.size(10.dp)) // Add top padding for aesthetics

                Text(
                    text = " About Drinkable?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(alignment = Alignment.Start)
                )

                Spacer(modifier = Modifier.size(16.dp)) // Space between title and description

                Text(
                    text = "Our system analyzes water quality using nine key parameters: pH, Hardness, Solids, Chloramines, Sulfate, Conductivity, Organic Carbon, Trihalomethanes, and Turbidity. By processing these inputs, it determines whether the water is safe for consumption. This solution leverages advanced algorithms to ensure reliable and accurate predictions, aiding in maintaining health and safety standards.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(16.dp)) // Space between title and description

                Text(
                    text = "I am an Android developer with a passion for AI research, constantly striving to create innovative solutions that merge technology with real-world applications. With experience in building user-centric mobile applications and a keen interest in the evolving field of artificial intelligence, I aim to contribute to projects that make a tangible impact.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(alignment = Alignment.End)
                )
            }

            Spacer(modifier = Modifier.size(5.dp))

            SelfContent(modifier = Modifier.fillMaxWidth()) // Align SelfContent at the bottom

            Spacer(modifier = Modifier.size(5.dp))
        }
    }
}


@Composable
fun SelfContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append("You can follow my work ")
        }

        pushStringAnnotation(tag = "https", annotation = "https://github.com/thesua7")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)) {
            append("thesua7")
        }
        pop()
    }

    ClickableText(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        onClick = { offset ->
            text.getStringAnnotations(tag = "https", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    // Open the URL (GitHub profile) in the browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
        }
    )
}


@Composable
fun LottieAnimationLoader(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.water_splashing_lottie))
    // Animate with repeat mode and infinite loop
    val progress by animateLottieCompositionAsState(
        composition, iterations = Int.MAX_VALUE // Loop the animation indefinitely
    )

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background), // Set background for clarity
        contentAlignment = Alignment.Center
    ) {
        // Ensure the Lottie animation fills the whole screen
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize() // Fill available size
        )
    }
}
