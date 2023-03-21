package com.udacity.todo.tasklist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.todo.data.source.TestTasksRepository
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.util.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * If I need an application context,I should use androidx test library
 * AndroidX Test library -> provides test versions of components like application and activities
 * Robolectric library -> Create a simulated android environment for local tests
 *
 * Test Runner
 * Default test runner run on local machine
 * AndroidJUnit4 runner - in local test -> run on robolectric simulated environment
 *                        in instrumented test -> run on device or emulator
 */

//RobolectricTestRunner::class
@RunWith(AndroidJUnit4::class)
class TaskListViewModelTest {

    private lateinit var taskListViewModel: TaskListViewModel

    /**
     * It run all architecture components related background jobs in the same thread
     * This ensures that the test result happen synchronously
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val repository = TestTasksRepository()
        taskListViewModel = TaskListViewModel(application, repository)
    }

    @Test
    fun addNewTask_setsNewTaskEvent_emitLiveData() {

        //When navigate for a new task
        taskListViewModel.navigateToNewTask()

        //Make sure live data is observed
        val value = taskListViewModel.newTaskEvent.getOrAwaitValue()

        //Then new task event is triggered
        assertThat(value).isNotNull()

    }

    @Test
    fun setFiltering_setAllTasks_tasksAddViewVisible() {

        // When the filter type is ALL_TASKS
        taskListViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        val value = taskListViewModel.tasksAddViewVisible.getOrAwaitValue() //Observe livedata
        assertThat(value).isTrue()

    }

}

