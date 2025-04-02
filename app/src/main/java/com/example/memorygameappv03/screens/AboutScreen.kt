package com.example.memorygameappv03.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\uD83D\uDCF1 Memory Game",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        InfoItem(emoji = "\uD83E\uDDE0", text = "A fun memory challenge game built using Kotlin & Jetpack Compose.")
        InfoItem(emoji = "\uD83D\uDC68\u200D\ud83D\uDCBB", text = "Developed by: Darsan Sabu George")
        InfoItem(emoji = "\uD83D\uDEE0️", text = "Tech Stack: Jetpack Compose, MVVM, Kotlin, Navigation")
        InfoItem(emoji = "\uD83C\uDFAF", text = "Objective: Tap the tiles you remember. Progress through levels. Score high!")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.navigate("welcome") }) {
            Text("Back to Welcome")
        }
    }
}

@Composable
fun InfoItem(emoji: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Text(text = emoji, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
