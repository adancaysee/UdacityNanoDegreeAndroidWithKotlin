package com.udacity.todo.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.udacity.todo.data.FakeTasksRepository
import com.udacity.todo.statics.StaticsViewModel
import com.udacity.todo.util.MainDispatcherRule
import com.udacity.todo.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StaticsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Subject under test
    private lateinit var statisticsViewModel: StaticsViewModel

    private lateinit var fakeTasksRepository: FakeTasksRepository


    @Before
    fun setUp() {
        fakeTasksRepository = FakeTasksRepository()
        statisticsViewModel = StaticsViewModel(fakeTasksRepository)
    }

    @Test
    fun refresh_loading() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())
        // Load the task in the viewmodel
        statisticsViewModel.refresh()
        // Then progress indicator is shown
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue()).isTrue()
        // Execute pending coroutine actions
        advanceUntilIdle()
        // Then progress indicator is hidden
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue()).isFalse()
    }

}