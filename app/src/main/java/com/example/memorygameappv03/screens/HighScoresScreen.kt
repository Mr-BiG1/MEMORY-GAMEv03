package com.example.memorygameappv03.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memorygameappv03.manager.HighScoreManager
import com.example.memorygameappv03.manager.ScoreEntry  // ✅ FIXED IMPORT

@Composable
fun HighScoresScreen(navController: NavController) {
    val context = LocalContext.current
    var highScores by remember { mutableStateOf(listOf<ScoreEntry>()) }

    // Load from HighScoreManager
    LaunchedEffect(Unit) {
        highScores = HighScoreManager.loadTopScores(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🏆 High Scores", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        if (highScores.isEmpty()) {
            Text("No high scores yet.")
        } else {
            highScores.forEachIndexed { index, entry ->
                Text("${index + 1}. ${entry.name}: ${entry.score} pts", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("welcome") }) {
            Text("Back to Welcome")
        }
    }
}
