package com.udacity.devbyteviewer.videos_dev_byte

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.devbyteviewer.database.DevByteDatabase
import com.udacity.devbyteviewer.database.DevByteVideoEntity
import com.udacity.devbyteviewer.network.RemoteVideosDataSource
import com.udacity.devbyteviewer.network.DevByteApiStatus
import com.udacity.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.launch

class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<DevByteApiStatus>()

    private val database = DevByteDatabase.getInstance(application.applicationContext)
    private val dao = database.dao


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
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch {
            _status.value = DevByteApiStatus.Loading
            try {
                val listContainer = RemoteVideosDataSource.service.getPlayList()
                _status.value = DevByteApiStatus.Success(listContainer.videos)
                savePlayList(listContainer.videos.asDatabaseModel())
            } catch (e: Throwable) {
                _status.value = DevByteApiStatus.Failure(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun savePlayList(list: List<DevByteVideoEntity>) {
        viewModelScope.launch {
            dao.insertPlayList(list)
        }
    }



}
