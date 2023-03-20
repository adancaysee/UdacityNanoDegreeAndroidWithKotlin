package com.udacity.todo

import com.google.common.truth.Truth.assertThat
import com.udacity.todo.data.domain.Task
import com.udacity.todo.statics.getActiveAndCompletedStats
import org.junit.Test

/**
 * funName_case_result
 */

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        val tasks = listOf(
            Task(title = "title", description = "desc", isCompleted = false)
        )
        // When the list of tasks is computed with an active task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 100 and 0
        assertThat(result.activeTasksPercent).isEqualTo(100f)
        assertThat(result.completedTasksPercent).isEqualTo(0f)
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {
        val tasks = listOf(
            Task(title = "title", description = "desc", isCompleted = true)
        )
        // When the list of tasks is computed with a completed task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 0 and 100
        assertThat(result.activeTasksPercent).isEqualTo(0f)
        assertThat(result.completedTasksPercent).isEqualTo(100f)
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        // Given 3 completed tasks and 2 active tasks
        val tasks = listOf(
            Task(title = "title", description = "desc", isCompleted = true),
            Task(title = "title", description = "desc", isCompleted = true),
            Task(title = "title", description = "desc", isCompleted = true),
            Task(title = "title", description = "desc", isCompleted = false),
            Task(title = "title", description = "desc", isCompleted = false),
        )
        // When the list of tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertThat(result.activeTasksPercent).isEqualTo(40f)
        assertThat(result.completedTasksPercent).isEqualTo(60f)
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent).isEqualTo(0f)
        assertThat(result.completedTasksPercent).isEqualTo(0f)
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        // When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent).isEqualTo(0f)
        assertThat(result.completedTasksPercent).isEqualTo(0f)
    }
}
