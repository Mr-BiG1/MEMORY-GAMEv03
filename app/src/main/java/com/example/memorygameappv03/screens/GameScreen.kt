package com.example.memorygameappv03.screens

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.memorygameappv03.R
import com.example.memorygameappv03.manager.HighScoreManager
import com.example.memorygameappv03.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    navController: NavController,
    playerName: String,
    viewModel: GameViewModel = viewModel()
) {
    val context = LocalContext.current
    var timer by remember { mutableIntStateOf(0) }

    // Play correct sound
    LaunchedEffect(viewModel.playCorrectSound) {
        if (viewModel.playCorrectSound) {
            MediaPlayer.create(context, R.raw.correct)?.start()
            viewModel.playCorrectSound = false
        }
    }

    // Play wrong sound
    LaunchedEffect(viewModel.playWrongSound) {
        if (viewModel.playWrongSound) {
            MediaPlayer.create(context, R.raw.wrong)?.start()
            viewModel.playWrongSound = false
        }
    }

    // Start a new round initially
    LaunchedEffect(Unit) {
        viewModel.startNewRound()
        viewModel.startMemoryPhase()
    }

    // Timer duration calculation based on round
    fun calculateTimerSeconds(round: Int): Int {
        return when {
            round < 3 -> 8
            round in 3..5 -> 10
            else -> 12
        }
    }

    // Countdown Timer Effect on each round/input phase
    LaunchedEffect(viewModel.round, viewModel.isInputPhase) {
        if (viewModel.isInputPhase && !viewModel.isGameOver) {
            timer = calculateTimerSeconds(viewModel.round)
            while (timer > 0 && viewModel.isInputPhase && !viewModel.isGameOver) {
                delay(1000)
                timer--
            }
            if (timer == 0 && viewModel.isInputPhase && !viewModel.isGameOver) {
                viewModel.endGame()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Player: $playerName", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
        Text("Round: ${viewModel.round}", style = MaterialTheme.typography.bodyLarge)
        Text("Score: ${viewModel.score}", style = MaterialTheme.typography.bodyLarge)

        if (viewModel.isInputPhase && !viewModel.isGameOver) {
            Text("Time left: $timer sec", style = MaterialTheme.typography.bodyMedium, color = Color.Blue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val gridSize = viewModel.gridSize
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            for (row in 0 until gridSize) {
                Row(horizontalArrangement = Arrangement.Center) {
                    for (col in 0 until gridSize) {
                        val index = row * gridSize + col
                        val isHighlighted = viewModel.correctTiles.contains(index)
                        val isSelected = viewModel.selectedTiles.contains(index)

                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(40.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                                .background(
                                    when {
                                        viewModel.isMemoryPhase && isHighlighted -> Color.Yellow
                                        isSelected -> Color.Green
                                        viewModel.isGameOver && isHighlighted -> Color.Red
                                        else -> Color.White
                                    }
                                )
                                .clickable(enabled = viewModel.isInputPhase && !viewModel.isGameOver) {
                                    viewModel.selectTile(index)
                                }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.isGameOver) {
            Text("Game Over!", color = Color.Red, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.resetGame()
                viewModel.startNewRound()
                viewModel.startMemoryPhase()
            }) {
                Text("Play Again")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if (playerName.isNotBlank()) {
                    HighScoreManager.saveScore(context, playerName, viewModel.score)
                    navController.navigate("high_scores")
                }
            }) {
                Text("Save Score & Exit")
            }
        }
    }
}
