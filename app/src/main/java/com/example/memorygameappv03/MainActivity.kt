package com.example.memorygameappv03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.memorygameappv03.screens.GameScreen
import com.example.memorygameappv03.screens.HighScoresScreen
import com.example.memorygameappv03.screens.WelcomeScreen
import com.example.memorygameappv03.ui.theme.MemoryGameAppv03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryGameAppv03Theme {
                val navController = rememberNavController()
                NavigationHost(navController)
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("game/{playerName}") { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Guest"
            GameScreen(navController, playerName)
        }
        composable("high_scores") {
            HighScoresScreen(navController)
        }
    }
}
