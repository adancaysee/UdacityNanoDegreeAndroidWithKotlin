package com.udacity.todo


import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksRepository
import com.udacity.todo.util.DataBindingIdlingResource
import com.udacity.todo.util.EspressoIdlingResource
import com.udacity.todo.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TaskActivityTest {

    private lateinit var tasksRepository: TasksRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        tasksRepository = ServiceLocator.providesTasksRepository(getApplicationContext())
        runBlocking {
            tasksRepository.deleteTasks()
        }
    }

    @After
    fun tearDown() {
        ServiceLocator.resetRepository()
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun editTask() = runBlocking {
        //GIVEN
        val task = Task("TITLE1", "DESCRIPTION")
        tasksRepository.saveTask(task)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //WHEN
        //Go to detail and update task and return
        onView(withText(task.title)).perform(click())
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(task.title)))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(task.description)))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isNotChecked()))

        // Click on the edit button,edit and save
        val updatedTask = task.copy("NEW TITLE", "NEW DESCRIPTION")
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText(updatedTask.title))
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText(updatedTask.description))
        onView(withId(R.id.save_task_fab)).perform(click())

        onView(withText(task.title)).check(doesNotExist())
        onView(withText(updatedTask.title)).check(matches(isDisplayed()))

        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Test
    fun createOneTask_deleteTask() = runBlocking {

        val task = Task("Back button", "Description")
        tasksRepository.saveTask(task)

        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withText(task.title)).check(matches(isDisplayed()))

        // Add active task
        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(typeText("TITLE1"))
        onView(withId(R.id.add_task_description_edit_text)).perform(
            typeText("DESCRIPTION"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.save_task_fab)).perform(click())
        onView(withText(task.title)).check(matches(isDisplayed()))
        // Open it in details view
        onView(withText("TITLE1")).perform(click())
        // Click delete task in menu
        onView(withId(R.id.menu_delete)).perform(click())

        // Verify it was deleted
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withText(R.string.nav_all)).perform(click())
        onView(withText("TITLE1")).check(doesNotExist())

        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }
}