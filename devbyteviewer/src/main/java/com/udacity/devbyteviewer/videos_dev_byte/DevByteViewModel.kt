package com.udacity.devbyteviewer.videos_dev_byte

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.devbyteviewer.network.DevByteApiClient
import com.udacity.devbyteviewer.network.DevByteApiStatus
import kotlinx.coroutines.launch

class DevByteViewModel : ViewModel() {

    private val _status = MutableLiveData<DevByteApiStatus>()


    val playList = Transformations.map(_status) {
        when (_status.value) {
            is DevByteApiStatus.Success -> (_status.value as DevByteApiStatus.Success).list
            else -> null
        }
    }

    val loadingVisibility = Transformations.map(_status) {
        when (_status.value) {
            is DevByteApiStatus.Loading -> View.VISIBLE
            else -> View.GONE
        }
    }


    init {
        getPlayList()
    }

    private fun getPlayList() {
        viewModelScope.launch {
            _status.value = DevByteApiStatus.Loading
            try {
                val listContainer = DevByteApiClient.service.getPlayList()
                _status.value = DevByteApiStatus.Success(listContainer.videos)
            } catch (e: Throwable) {
                _status.value = DevByteApiStatus.Failure(e.message ?: "Unknown error occurred")
            }
        }
    }

}