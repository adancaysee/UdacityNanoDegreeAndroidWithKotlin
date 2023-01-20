package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.network.MarsProperty
import com.example.marsrealestate.network.MarsRetrofitClient
import kotlinx.coroutines.launch

enum class MarsApiStatus {
    LOADING, SUCCESS, FAILURE
}

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _marsProperties = MutableLiveData<List<MarsProperty>>()
    val marsProperties: LiveData<List<MarsProperty>>
        get() = _marsProperties

    init {
        getRealEstates()
    }

    private fun getRealEstates() {
        _status.value = MarsApiStatus.LOADING
        viewModelScope.launch {
            try {
                _status.value = MarsApiStatus.SUCCESS
                val list = MarsRetrofitClient.marsApiService.getRealEstates()
                _marsProperties.value = list
            }catch (e:Exception) {
                _status.value = MarsApiStatus.FAILURE
            }
        }
    }


}