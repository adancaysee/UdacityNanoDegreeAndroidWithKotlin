package com.udacity.todo.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.todo.ServiceLocator
import com.udacity.todo.R
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.FakeAndroidTasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    private lateinit var fakeRepository: FakeAndroidTasksRepository

    @Before
    fun setUp() {
        fakeRepository = FakeAndroidTasksRepository()
        ServiceLocator.tasksRepository = fakeRepository
    }

    @After
    fun tearDown() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTask_displayedInUI() = runTest {
        // GIVEN - Add active task to the db
        val activeTask = Task("Active Task", "AndroidX Rocks", false)
        fakeRepository.saveTask(activeTask)

        val bundle = TaskDetailFragmentArgs.Builder(activeTask.id).build().toBundle()

        //WHEN - Detail fragment launched to display task
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.Theme_TodoApp)
        withContext(Dispatchers.IO) { delay(2000) }

    }


}