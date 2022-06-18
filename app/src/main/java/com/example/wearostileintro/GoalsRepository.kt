package com.example.wearostileintro

import kotlinx.coroutines.delay
import kotlin.random.Random

data class GoalProgess(
    val current: Int,
    val goal: Int,
) {
    val percentage get() = current.toFloat() / goal.toFloat()
}

object GoalsRepository {
    suspend fun getGoalProgress(): GoalProgess {
        delay(200)
        return goalProgress.copy(current = listOf(1000, 2000, 4000, 5000).random())
    }
}

val goalProgress = GoalProgess(current = 50, goal = 8000)