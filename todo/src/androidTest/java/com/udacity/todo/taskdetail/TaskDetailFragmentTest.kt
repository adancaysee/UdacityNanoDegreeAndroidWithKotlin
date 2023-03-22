package com.udacity.todo.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.todo.R
import com.udacity.todo.data.domain.Task
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TaskDetailFragmentTest {


    @Test
    fun activeTask_displayedInUI() {
        // GIVEN - Add active task to the db
        val activeTask = Task("Active Task", "AndroidX Rocks", false)

        val bundle = TaskDetailFragmentArgs.Builder(activeTask.id).build().toBundle()

        //WHEN - Detail fragment launched to display task
        launchFragmentInContainer<TaskDetailFragment>(bundle,R.style.Theme_TodoApp)
        Thread.sleep(2000)

    }

}