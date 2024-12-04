package com.thesua7.drinkable.screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.About)

    Surface(
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White
        ) {
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            items.forEach { item ->
                val isSelected = currentDestination?.route == item.route

                NavigationBarItem(
                    icon = {
                        AnimatedContent(
                            targetState = isSelected,
                            transitionSpec = { fadeIn() togetherWith fadeOut() }, label = ""
                        ) { selected ->
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
//                                tint = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(if (selected) 30.dp else 24.dp)
                            )
                        }
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}
