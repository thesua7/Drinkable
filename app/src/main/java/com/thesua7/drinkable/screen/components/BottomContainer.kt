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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.About)

    Surface(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
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
                                painter = painterResource(item.iconRes),
                                contentDescription = item.label,
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
                        selectedIconColor = MaterialTheme.colorScheme.background,
                        indicatorColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}
