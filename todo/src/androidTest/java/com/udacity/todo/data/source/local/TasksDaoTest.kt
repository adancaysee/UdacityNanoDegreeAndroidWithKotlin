package com.udacity.todo.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.domain.asDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var inMemoryDatabase: ToDoDatabase

    @Before
    fun setUp() {
        inMemoryDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        inMemoryDatabase.close()
    }

    @Test
    fun insertTask_getById() = runTest {
        // GIVEN - Insert a task.
        val task = Task("title", "description", false)
        inMemoryDatabase.tasksDao.insertTask(task.asDatabase())

        //WHEN - Get the task by id from the database.
        val loadedTask = inMemoryDatabase.tasksDao.getTaskById(task.id)

        assertThat(loadedTask).isNotNull()
        assertThat(loadedTask?.title).isEqualTo(task.title)
        assertThat(loadedTask?.description).isEqualTo(task.description)
        assertThat(loadedTask?.isCompleted).isEqualTo(task.isCompleted)
        assertThat(loadedTask?.id).isEqualTo(task.id)

    }

    @Test
    fun updateTaskAndGetById() {
        // GIVEN - Insert a task into the DAO.
        val task = Task("title", "description", false)
        inMemoryDatabase.tasksDao.insertTask(task.asDatabase())

        // WHEN - Update the task by creating a new task with the same ID but different attributes.
        val updatedTask = task.copy(title = "New title")
        inMemoryDatabase.tasksDao.updateTask(updatedTask.asDatabase())

        // THEN - Check that when you get the task by its ID, it has the updated values.
        val loadedTask = inMemoryDatabase.tasksDao.getTaskById(task.id)

        assertThat(loadedTask).isNotNull()
        assertThat(loadedTask?.title).isEqualTo(updatedTask.title)
    }
}

