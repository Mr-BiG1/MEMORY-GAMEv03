package com.example.memorygameappv03.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameViewModel : ViewModel() {
    var gridSize by mutableStateOf(6)
    var round by mutableStateOf(1)
    var score by mutableStateOf(0)
    var correctTiles = mutableStateListOf<Int>()
    var selectedTiles = mutableStateListOf<Int>()
    var isMemoryPhase by mutableStateOf(false)
    var isInputPhase by mutableStateOf(false)
    var isGameOver by mutableStateOf(false)
    var timer by mutableStateOf(0)

    private var timerJob: Job? = null

    private val highlightCount: Int
        get() = if (round >= 4) 5 else 4

    fun startNewRound() {
        isGameOver = false
        isMemoryPhase = true
        isInputPhase = false
        selectedTiles.clear()
        correctTiles.clear()

        val totalTiles = gridSize * gridSize
        val newSet = mutableSetOf<Int>()
        while (newSet.size < highlightCount) {
            newSet.add(Random.nextInt(totalTiles))
        }
        correctTiles.addAll(newSet)
    }

    fun startMemoryPhase() {
        viewModelScope.launch {
            isMemoryPhase = true
            isInputPhase = false
            delay(3000)
            if (!isGameOver) {
                isMemoryPhase = false
                isInputPhase = true
                startCountdown(calculateTimerSeconds(round))
            }
        }
    }

    fun selectTile(index: Int) {
        if (!isInputPhase || isGameOver || selectedTiles.contains(index)) return
        selectedTiles.add(index)

        if (selectedTiles.size == correctTiles.size) {
            checkUserInput()
        }
    }

    fun checkUserInput() {
        if (selectedTiles.toSet() == correctTiles.toSet()) {
            score += if (round >= 4) 20 else 10
            round++
            startNewRoundWithDelay()
        } else {
            endGame()
        }
    }

    private fun startNewRoundWithDelay() {
        viewModelScope.launch {
            delay(1000)
            startNewRound()
            startMemoryPhase()
        }
    }

    private fun startCountdown(duration: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            timer = duration
            while (timer > 0 && isInputPhase && !isGameOver) {
                delay(1000)
                timer--
            }
            if (timer == 0 && isInputPhase) {
                endGame()
            }
        }
    }

    private fun calculateTimerSeconds(round: Int): Int = if (round >= 5) 8 else 6

    fun endGame() {
        isGameOver = true
        isInputPhase = false
        isMemoryPhase = false
        timerJob?.cancel()
    }

    fun resetGame() {
        round = 1
        score = 0
        gridSize = 6
        correctTiles.clear()
        selectedTiles.clear()
        isGameOver = false
        isMemoryPhase = false
        isInputPhase = false
    }
}
