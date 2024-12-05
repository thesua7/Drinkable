package com.thesua7.drinkable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thesua7.drinkable.screen.AboutScreen
import com.thesua7.drinkable.screen.PredictionEvent
import com.thesua7.drinkable.screen.PredictionScreen
import com.thesua7.drinkable.screen.SharedViewModel
import com.thesua7.drinkable.screen.components.BottomNavItem
import com.thesua7.drinkable.screen.components.BottomNavigationBar
import com.thesua7.drinkable.screen.components.BottomSheetContent
import com.thesua7.drinkable.screen.components.TopBar
import com.thesua7.drinkable.ui.theme.DrinkableTheme
import com.thesua7.drinkable.ui.theme.SplashScreenWithLottie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupComposableContent()
    }

    private fun setupComposableContent() {
        setContent {
            DrinkableTheme {
                MainActivityContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent(viewModel: SharedViewModel) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden, skipHiddenState = false
        )
    )
    val coroutineScope = rememberCoroutineScope()

    // Get the current route
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Splash Screen as a Full-Screen Composable
    if (currentRoute == "splash") {
        SplashScreenWithLottie {
            navController.navigate(BottomNavItem.Home.route) {
                popUpTo("splash") { inclusive = true }
            }
        }
    } else {
        // Main App Content with Scaffold
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                BottomSheetContent {
                    viewModel.onEvent(PredictionEvent.Predict(it))
                    showBottomSheet = false
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                }
            },
            sheetPeekHeight = 0.dp
        ) {
            Scaffold(
                topBar = {
                    TopBar(navController = navController, onAddClick = { showBottomSheet = true })
                },
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) { contentPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "splash",
                    modifier = Modifier.padding(contentPadding)
                ) {
                    composable("splash") {
                        // This should only be used temporarily; it's replaced by the full-screen splash
                    }
                    composable(BottomNavItem.Home.route) {
                        PredictionScreen(viewModel)
                    }
                    composable(BottomNavItem.About.route) {
                        AboutScreen()
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManageBottomSheetState(
    showBottomSheet: Boolean, scaffoldState: BottomSheetScaffoldState, onBottomSheetHide: () -> Unit
) {
    if (showBottomSheet) {
        LaunchedEffect(Unit) {
            scaffoldState.bottomSheetState.expand()
            onBottomSheetHide()
        }
    }
}
