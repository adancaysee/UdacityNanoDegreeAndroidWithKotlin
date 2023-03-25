package com.udacity.todo.tasklist


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.udacity.todo.R
import com.udacity.todo.ServiceLocator
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.FakeAndroidTasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
class TaskListFragmentTest {

    private lateinit var fakeTasksRepository: FakeAndroidTasksRepository

    @Before
    fun setUp() {
        fakeTasksRepository = FakeAndroidTasksRepository()
        ServiceLocator.tasksRepository = fakeTasksRepository
    }

    @After
    fun tearDown() {
        ServiceLocator.resetRepository()
    }


    @Test
    fun clickFirstItem_navigateToDetailFragment() = runTest {
        //GIVEN two task for list fragment
        val task1 = Task("TITLE1", "DESCRIPTION1", false, "id1")
        val task2 = Task("TITLE2", "DESCRIPTION2", true, "id2")
        fakeTasksRepository.saveTask(task1)
        fakeTasksRepository.saveTask(task2)

        val scenario = launchFragmentInContainer<TaskListFragment>(Bundle(), R.style.Theme_TodoApp)

        val mockNavController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, mockNavController)
        }

        //WHEN - Click on the first list item
        onView(withId(R.id.tasks_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        //THEN - Navigated to detail for first item
        verify(mockNavController).navigate(TaskListFragmentDirections.actionOpenTask(task1.id))

    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() {

        val scenario = launchFragmentInContainer<TaskListFragment>(Bundle(), R.style.Theme_TodoApp)
        val mockNavController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, mockNavController)
        }

        //WHEN - Click on the fab button
        onView(withId(R.id.add_task_fab))
            .perform(click())

        //THEN - Navigated to AddEditTaskFragment
        verify(mockNavController).navigate(
            TaskListFragmentDirections.actionAddTask(
                null,
                getApplicationContext<Context>().getString(R.string.add_task)
            )
        )

    }
}