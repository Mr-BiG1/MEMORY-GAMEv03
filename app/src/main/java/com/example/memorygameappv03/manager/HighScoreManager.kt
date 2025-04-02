package com.example.memorygameappv03.manager

import android.content.Context

data class ScoreEntry(val name: String, val score: Int)

object HighScoreManager {

    private const val PREFS_NAME = "scores"
    private const val SCORES_KEY = "high_scores"

    fun saveScore(context: Context, name: String, score: Int) {
        if (name.isBlank()) return

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getStringSet(SCORES_KEY, setOf())?.toMutableSet() ?: mutableSetOf()

        existing.add("$name:$score")

        val topScores = existing
            .mapNotNull {
                val parts = it.split(":")
                if (parts.size == 2) ScoreEntry(parts[0], parts[1].toIntOrNull() ?: 0) else null
            }
            .sortedByDescending { it.score }
            .take(3) // Top 3 scores
            .map { "${it.name}:${it.score}" }
            .toSet()

        prefs.edit().putStringSet(SCORES_KEY, topScores).apply()
    }

    fun loadTopScores(context: Context): List<ScoreEntry> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val rawSet = prefs.getStringSet(SCORES_KEY, setOf()) ?: setOf()

        return rawSet
            .mapNotNull {
                val parts = it.split(":")
                if (parts.size == 2) ScoreEntry(parts[0], parts[1].toIntOrNull() ?: 0) else null
            }
            .sortedByDescending { it.score }
            .take(3)
    }
}
