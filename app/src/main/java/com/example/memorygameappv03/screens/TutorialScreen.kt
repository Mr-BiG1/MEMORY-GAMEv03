package com.example.memorygameappv03.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TutorialScreen(navController: NavController) {
    val steps = listOf(
        "🔶 Watch carefully! The yellow tiles will light up for 3 seconds.",
        "🟢 Now it’s your turn. Tap the tiles in the same order you saw.",
        "💥 Be quick! You have a limited time to select.",
        "🎯 Each correct round scores points. Can you beat your high score?"
    )

    var currentStep by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("📖 How to Play", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))
        Text(steps[currentStep], style = MaterialTheme.typography.bodyLarge, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))

        Row {
            if (currentStep > 0) {
                Button(onClick = { currentStep-- }, modifier = Modifier.padding(end = 8.dp)) {
                    Text("Back")
                }
            }

            if (currentStep < steps.size - 1) {
                Button(onClick = { currentStep++ }) {
                    Text("Next")
                }
            } else {
                Button(onClick = { navController.navigate("welcome") }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("Let's Play!")
                }
            }
        }
    }
}
