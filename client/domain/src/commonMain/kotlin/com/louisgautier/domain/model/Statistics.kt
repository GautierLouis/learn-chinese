package com.louisgautier.domain.model

import kotlin.time.Duration

data class Statistics(
    val totalScore: Int,
    val averageTime: Duration,
    val averageDifficulty: Difficulty?,
    val currentDayStreak: Int,
    val sessionCount: Int
)