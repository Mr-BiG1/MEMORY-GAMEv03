package com.example.memorygameappv03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.memorygameappv03.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "welcome") {
                composable("welcome") {
                    WelcomeScreen(navController)
                }
                composable("tutorial") {
                    TutorialScreen(navController)
                }
                composable(
                    route = "game/{playerName}",
                    arguments = listOf(navArgument("playerName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
                    GameScreen(navController, playerName)
                }
                composable("high_scores") {
                    HighScoresScreen(navController)
                }
            }
        }
    }
}
