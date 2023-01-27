package com.udacity.devbyteviewer.videos_dev_byte

import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udacity.devbyteviewer.DevByteViewerApplication
import com.udacity.devbyteviewer.repository.DevByteApiStatus
import com.udacity.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DevByteViewModel(private val repository: VideosRepository) : ViewModel() {

    private val _status = MutableLiveData<DevByteApiStatus>()

    val playList = repository.videos

    val loadingVisibility = Transformations.map(_status) {
        when (_status.value) {
            is DevByteApiStatus.Loading -> View.VISIBLE
            else -> View.GONE
        }
    }

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch {
            _status.value = DevByteApiStatus.Loading
            try {
                repository.refreshVideos()
                _status.value = DevByteApiStatus.Success
            } catch (e: IOException) {
                _status.value = DevByteApiStatus.Failure(e.message ?: "Unknown error occurred")
            } catch (e: HttpException) {
                _status.value = DevByteApiStatus.Failure(e.message ?: "Unknown error occurred")
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as DevByteViewerApplication
                val repository = application.appContainer.videosRepository
                DevByteViewModel(repository)
            }
        }
    }

}

