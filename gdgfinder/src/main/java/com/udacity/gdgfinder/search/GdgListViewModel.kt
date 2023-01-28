package com.udacity.gdgfinder.search

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udacity.gdgfinder.GdgFinderApplication
import com.udacity.gdgfinder.domain.GdgChapter
import com.udacity.gdgfinder.repository.GdgChapterRepository
import kotlinx.coroutines.launch
import timber.log.Timber

enum class GdgApiStatus { LOADING, ERROR, SUCCESS }

class GdgListViewModel(private val repository: GdgChapterRepository) : ViewModel() {

    private val _status = MutableLiveData<GdgApiStatus>()

    private val _chapters = MutableLiveData<List<GdgChapter>>()
    val chapter: LiveData<List<GdgChapter>>
        get() = _chapters

    val loadingVisibility = Transformations.map(_status) {
        when(_status.value) {
            GdgApiStatus.LOADING -> View.VISIBLE
            else -> View.GONE
        }
    }

    init {
        fetchChapters()
    }

    private fun fetchChapters() {
        viewModelScope.launch {
            _status.value = GdgApiStatus.LOADING
            try {
                val list = repository.getChapters()
                _chapters.value = list
                _status.value = GdgApiStatus.SUCCESS
            } catch (e: Throwable) {
                Timber.i(e.message)
                _status.value = GdgApiStatus.ERROR
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as GdgFinderApplication
                val repository = application.appContainer.gdgChapterRepository
                GdgListViewModel(repository)
            }

        }
    }

}