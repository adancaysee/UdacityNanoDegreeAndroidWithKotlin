package com.udacity.todo.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
import org.hamcrest.Matchers.not
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
    fun tearDown() = runTest {
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

        //THEN - TaskDetail are displayed on the screen
        //Make sure title,description and checkbox views are shown and correct
        onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(activeTask.title)))
        onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
        /**
         * If I read this test ;
         *  The view(R.id.task_detail_description_text)) --> Check it's text matches with text(activeTask.description)
         */
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(activeTask.description)))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
        //it asserts that the state of the checkbox is actually checked
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))

    }

    @Test
    fun completedTask_inDisplayedUi() = runTest {
        // GIVEN - Add completed task to the db
        val completedTask = Task("Completed Task", "AndroidX Rocks", true)
        fakeRepository.saveTask(completedTask)

        //WHEN - Detail fragment launched to display task
        val bundle = TaskDetailFragmentArgs.Builder(completedTask.id).build().toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle,R.style.Theme_TodoApp)

        onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText(completedTask.title)))
        onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText(completedTask.description)))
    }


}