package com.udacity.todo.data.source

import com.google.common.truth.Truth.assertThat
import com.udacity.todo.data.source.local.TaskEntity
import com.udacity.todo.data.source.local.TestTaskDao
import com.udacity.todo.data.source.local.asDomain
import com.udacity.todo.data.source.remote.TestTasksNetworkDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest {

    private val task1 = TaskEntity(title = "Title1", description = "Description1")
    private val task2 = TaskEntity(title = "Title2", description = "Description2")
    private val task3 = TaskEntity(title = "Title3", description = "Description3")
    private var remoteTasks = listOf(task1,task2)
    private var localTasks = listOf(task3)

    private lateinit var testTaskDao: TestTaskDao

    private lateinit var testTasksNetworkDataSource: TestTasksNetworkDataSource

    private lateinit var defaultTasksRepository: DefaultTasksRepository


    @Before
    fun setUp() {
        testTaskDao = TestTaskDao()
        testTasksNetworkDataSource = TestTasksNetworkDataSource()
        testTaskDao.fakeTasks = localTasks.toMutableList()
        testTasksNetworkDataSource.tasks = remoteTasks.toMutableList()

        defaultTasksRepository = DefaultTasksRepository(testTaskDao, testTasksNetworkDataSource)
    }

    //How can I test  getTask function of the repository ?
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTask_requestAllTasksFromRemoteDataSource() = runTest {

        //When tasks are requested from the repository
        val value = defaultTasksRepository.getTasks(true)

        // Then tasks are loaded from the remote data source
        assertThat(value).isEqualTo(remoteTasks.asDomain())

    }
}