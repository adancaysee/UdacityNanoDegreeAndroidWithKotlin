package com.udacity.todo.tasklist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.todo.data.source.FakeTasksRepository
import com.udacity.todo.util.getOrAwaitValue
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
@RunWith(AndroidJUnit4::class)
class TaskListViewModelTest {

    /**
     * It run all architecture components related background jobs in the same thread
     * This ensures that the test result happen synchronously
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent_emitLiveData() {
        //Given a refresh ViewModel
        val application = ApplicationProvider.getApplicationContext<Application>()
        val repository = FakeTasksRepository()
        val viewModel = TaskListViewModel(application, repository)

        //When navigate for a new task
        viewModel.navigateToNewTask()

        //Make sure live data is observed
        val value = viewModel.newTaskEvent.getOrAwaitValue()

        //Then new task event is triggered
        assertThat(value).isNotNull()

    }

}

