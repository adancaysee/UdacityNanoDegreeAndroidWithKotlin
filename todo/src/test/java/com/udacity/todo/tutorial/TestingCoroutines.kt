package com.udacity.todo.tutorial

/**
 * COROUTINE TESTING
 * Test code should run synchronously --> for not flaky tests
 * Synchronization Mechanism --> tell the test execution to wait until the asynchronous work finishes
 * Testing async requires determinism and synchronization mechanism*
 *
 * ** runTest **
 * runTest -> is a coroutine builder designed for testing. This blocks test thread and suspend functions runs and resume assertions
 * Dispatcher.Main uses Android Main Looper. If we run test and this test uses viewModelScope.launch, below error is showing
 * ERROR:
 * Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
 * viewModelScope --> This coroutine scope is tied to viewModel.
 *                --> When the viewModel is canceled, this scope is automatically canceled
 *                --> Uses Dispatchers.Main for coroutine dispatcher
 *                --> * Coroutine Scope --> Controls lifetime of a coroutine
 *                --> * Coroutine Dispatcher --> Controls what thread the coroutine code runs on
 *
 * ** TEST DISPATCHER **
 * When THE code creates new coroutines other than the top-level test coroutine that runTest creates,
 * I’ll need to control how those new coroutines are scheduled by choosing the appropriate TestDispatcher.
 * I should inject test dispatchers to replace real dispatchers. (withContext(testDispatcher))
 */