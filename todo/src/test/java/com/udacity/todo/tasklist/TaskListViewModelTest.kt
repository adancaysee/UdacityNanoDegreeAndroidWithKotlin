package com.udacity.todo.tasklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.udacity.todo.R
import com.udacity.todo.data.FakeTasksRepository
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.util.MainDispatcherRule
import com.udacity.todo.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class) --> This provide main coroutine
class TaskListViewModelTest {

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var taskListViewModel: TaskListViewModel

    private lateinit var fakeTasksRepository: FakeTasksRepository

    /**
     * It run all architecture components related background jobs in the same thread
     * This ensures that the test result happen synchronously
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        /**
         * Swap the standard Dispatcher.Main with test coroutine dispatcher
         * Test coroutine dispatcher doesn't use main looper (I don't use @RunWith(AndroidJUnit4::class) or android test I must do that)
         */
        //Dispatchers.setMain(testDispatcher) --> MainDispatcherRule is do that
        fakeTasksRepository = FakeTasksRepository()
        taskListViewModel = TaskListViewModel(fakeTasksRepository)
    }

    @After
    fun tearDown() {
       // Dispatchers.resetMain() --> MainDispatcherRule is do that
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

    @Test
    fun refreshTask_isEmittingTaskLiveData() {
        taskListViewModel.tasks.getOrAwaitValue()
    }

    @Test
    fun completeTask_dataAndSnackbarEventUpdated() = runTest {
        //GIVEN - Create an active task and add it to the repository
        val activeTask = Task("Title", "Description", false)
        fakeTasksRepository.saveTask(activeTask)

        //WHEN - Complete task
        taskListViewModel.changeTaskActivateStatus(activeTask, true)

        //THEN -- Task is completed and snackbar is updated
        assertThat(fakeTasksRepository.getTask(activeTask.id)?.isCompleted).isTrue()
        val snackBarIntEvent = taskListViewModel.snackbarIntEvent.getOrAwaitValue()
        assertThat(snackBarIntEvent.getContentIfNotHandled()).isEqualTo(R.string.task_marked_complete)

    }

}

