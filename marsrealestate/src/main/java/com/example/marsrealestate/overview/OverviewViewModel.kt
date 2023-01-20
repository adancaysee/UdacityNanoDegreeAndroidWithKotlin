package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marsrealestate.network.MarsRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class MarsApiStatus(status: String) {
    LOADING("loading"), SUCCESS("done"), FAILURE("failure")
}

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getRealEstates()
    }

    private fun getRealEstates() {
        _status.value = MarsApiStatus.LOADING
        MarsRetrofitClient.marsApiService.getRealEstates().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _status.value = MarsApiStatus.SUCCESS
                response.body()?.let {
                    _response.value = it
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _status.value = MarsApiStatus.FAILURE
            }

        })
    }


}