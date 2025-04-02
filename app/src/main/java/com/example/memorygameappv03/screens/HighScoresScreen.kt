package com.example.memorygameappv03.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memorygameappv03.R
import com.example.memorygameappv03.manager.HighScoreManager
import com.example.memorygameappv03.manager.ScoreEntry

@Composable
fun HighScoresScreen(navController: NavController) {
    val context = LocalContext.current
    var highScores by remember { mutableStateOf(listOf<ScoreEntry>()) }

    // Play 🎉 cheer sound and load scores
    LaunchedEffect(Unit) {
        MediaPlayer.create(context, R.raw.aahoo)?.start()
        highScores = HighScoreManager.loadTopScores(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🏆 High Scores",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (highScores.isEmpty()) {
            Text("No high scores yet.", fontSize = 16.sp)
        } else {
            highScores.forEachIndexed { index, entry ->
                val medal = when (index) {
                    0 -> "🥇"
                    1 -> "🥈"
                    2 -> "🥉"
                    else -> ""
                }
                Text(
                    text = "${index + 1}. $medal ${entry.name}: ${entry.score} pts",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = { navController.navigate("welcome") },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Back to Welcome")
        }
    }
}
