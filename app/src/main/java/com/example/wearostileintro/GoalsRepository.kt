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
        return goalProgress.copy(current = Random.nextInt())
    }
}

val goalProgress = GoalProgess(current = 0, goal = 8000)