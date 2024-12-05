package com.thesua7.drinkable.screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.thesua7.drinkable.R


@Composable
fun BottomSheetContent(
    onComplete: (FloatArray) -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        PredictionForm(
            onPredict = { inputs ->
                onComplete(inputs)
            },
        )

    }
}


sealed class BottomNavItem(val route: String, val label: String, @DrawableRes val iconRes: Int) {
    data object Home : BottomNavItem("home", "home", R.drawable.water_drop_svg)
    data object About : BottomNavItem("about", "about", R.drawable.about_svg)
}