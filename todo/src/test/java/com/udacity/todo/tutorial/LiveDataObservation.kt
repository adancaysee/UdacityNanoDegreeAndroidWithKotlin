package com.udacity.todo.tutorial

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.common.truth.Truth.assertThat
import com.udacity.todo.util.getOrAwaitValue
import org.junit.Rule
import org.junit.Test

class MyViewModel : ViewModel() {
    private val _liveData1 = MutableLiveData<String>()
    val liveData1: LiveData<String> = _liveData1

    val liveData2 = Transformations.map(_liveData1) {
        it.uppercase()
    }

    fun setNewValue(newValue: String) {
        _liveData1.value = newValue
    }
}

/**
 * If live data doesn't observed it doesn't too much
 * A LiveData that is not observed won’t emit updates
 */
class MyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Step1 - For live data observation

    @Test
    fun isLiveDataEmitting() {
        val viewModel = MyViewModel()
        viewModel.setNewValue("foo")

        val value1 = viewModel.liveData1.value
        val value2 = viewModel.liveData2.value

        assertThat(value1).isEqualTo("foo") //Passes
        assertThat(value2).isEqualTo("FOO") // Fails

    }

    @Test
    fun isLiveDataEmitting_myObserver() {
        val viewModel = MyViewModel()

        val observer = Observer<String> {}
        try {
            viewModel.liveData2.observeForever(observer) // Step2 - For live data observation

            viewModel.setNewValue("foo")
        } finally {
            viewModel.liveData2.removeObserver(observer)
        }

        val value1 = viewModel.liveData1.value
        val value2 = viewModel.liveData2.value

        assertThat(value1).isEqualTo("foo") //Passes
        assertThat(value2).isEqualTo("FOO") // Fails

    }

    @Test
    fun isLiveDataEmitting_getOrAwaitValue() {
        val viewModel = MyViewModel()
        viewModel.setNewValue("foo")

        val value1 = viewModel.liveData1.value
        val value2 = viewModel.liveData2.getOrAwaitValue() // Step2 - For live data observation

        assertThat(value1).isEqualTo("foo") //Passes
        assertThat(value2).isEqualTo("FOO") // Passes
    }

}