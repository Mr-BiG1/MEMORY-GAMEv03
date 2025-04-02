# 🧠 Memory Game App

A Kotlin-based Android memory challenge developed for **PROG3210 - Programming: Mobile Applications II**. Test your memory skills by remembering tile positions before time runs out!

## 🎯 Objective
- Memorize the highlighted tiles in a 6x6 grid.
- Select the correct tiles within the countdown timer.
- Progress through rounds to earn points.
- Game ends if time runs out or a wrong tile is selected.

---

## 📱 Features

### ✅ Navigation
- **Three Pages:** Welcome, Game, and High Scores.
- Smooth screen transitions using **Navigation Compose**.
- Back navigation supported.

### ✅ Welcome Screen
- Instructions on how to play the game.
- Input field to enter player's name.
- Buttons: **Start Game**, **View High Scores**, **How to Play (Tutorial)**.

### ✅ Game Screen
- Dynamic 6x6 tile grid.
- Tiles highlighted for 3 seconds during memory phase.
- 5-second timer to recall and select correct tiles.
- Progressive difficulty:
  - Rounds 1–3: 4 tiles (10 pts each).
  - Round 4+: 5 tiles (20 pts each).
- **Sound Effects** for correct/wrong selections.
- **Haptic feedback** on incorrect tile.
- **Scoring System** with MVVM architecture.

### ✅ High Scores
- Displays top 3 player scores.
- Only saves score if a name is entered.
- "Back to Welcome" navigation button.
- Plays a celebratory **cheers.mp3** sound on success!

---

## 🏗️ Architecture

This project follows **MVVM (Model-View-ViewModel)** architecture:
- `ViewModel`: Handles game state, logic, and timer.
- `Composable Screens`: Reactively render UI using `mutableState`.
- `Manager`: High score management using SharedPreferences.

---

## 🧪 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Navigation:** Navigation Compose
- **Sound:** `MediaPlayer`
- **Haptics:** `Vibrator` API
- **Persistence:** SharedPreferences

---

## 📂 Folder Structure

