package com.louisgautier.composeApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.louisgautier.composeApp.dictionary.DictionaryScreen
import com.louisgautier.composeApp.home.HomeScreen
import com.louisgautier.composeApp.session.SessionBuilderScreen
import com.louisgautier.composeApp.session.SessionCongratulationScreen
import com.louisgautier.composeApp.session.SessionScreen
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.firebase.event.Tracker
import com.louisgautier.logger.AppLogger
import com.louisgautier.utils.AppConfig
import org.koin.mp.KoinPlatform

@Composable
fun App() {

    MaterialTheme {
        val navController = rememberNavController()
        val firebaseManager = remember { KoinPlatform.getKoin().get<FirebaseManager>() }
        val remoteConfigManager = remember { KoinPlatform.getKoin().get<RemoteConfigManager>() }

        firebaseManager.initialize()

        LaunchedEffect(Unit) {
            remoteConfigManager.events.collect {

            }
        }

        LaunchedEffect(Unit) {
            Tracker.events.collect { event ->
                AppLogger.d(tag = "Tracking event", message = event.toString())
                firebaseManager.logEvent(event)
            }
        }

        LaunchedEffect(navController) {
            AppNavigation.navigationEvents.collect { event ->
                AppLogger.d(tag = "Navigation event", message = event.toString())
                when (event) {
                    is NavigationCommand.Navigate -> navController.navigate(event.route) {
                        if (event.clearBackStack) {
                            popUpTo(Route.HomeRoute) { inclusive = false }
                        }
                    }

                    is NavigationCommand.NavigateUp -> navController.navigateUp()
                    is NavigationCommand.NavigateHome -> navController.navigate(Route.HomeRoute) {
                        popUpTo(Route.HomeRoute) { inclusive = true }
                    }
                }
            }
        }

        Box {
            NavHost(
                navController = navController,
                startDestination = Route.HomeRoute,
            ) {
                composable<Route.HomeRoute> { HomeScreen() }
                composable<Route.SessionBuilderRoute> { SessionBuilderScreen() }
                composable<Route.SessionRoute> { SessionScreen() }
                composable<Route.DictionaryListRoute> { DictionaryScreen() }
                composable<Route.SessionCongratulationScreenRoute> { SessionCongratulationScreen() }
            }
            FlavorComponent()
        }
    }
}

@Composable
fun FlavorComponent() {
    val appConfig = remember { KoinPlatform.getKoin().get<AppConfig>() }

    if (!appConfig.isProduction) {
        Text(
            text = appConfig.flavor.uppercase(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .background(if (appConfig.flavor == "dev") Color.Red else Color.Blue)
        )
    }
}