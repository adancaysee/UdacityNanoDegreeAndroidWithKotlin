package com.udacity.todo.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Sets the main coroutines dispatcher to a [TestCoroutineScope] for unit testing. A [TestCoroutineScope] provides control over the execution of coroutines.
 * cleanupTestCoroutines() --> runTest`, which automatically performs the cleanup, instead of using this function.
 *
 * TestWatcher() --> is a TestRule, it makes MainDispatcherRule is a junit rule
 */

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
