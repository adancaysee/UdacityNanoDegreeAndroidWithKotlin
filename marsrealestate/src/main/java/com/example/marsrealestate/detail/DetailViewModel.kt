package com.example.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.marsrealestate.R
import com.example.marsrealestate.network.MarsProperty

class DetailViewModel(marsProperty: MarsProperty, application: Application) :
    AndroidViewModel(application) {

    private val _selectedMarsProperty = MutableLiveData<MarsProperty>()
    val selectedMarsProperty: LiveData<MarsProperty>
        get() = _selectedMarsProperty

    val displayPropertyPrice = Transformations.map(selectedMarsProperty) {
        application.applicationContext.getString(
            when (it.isRental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            },it.price
        )
    }

    val displayPropertyType = Transformations.map(selectedMarsProperty) {
        application.applicationContext.getString(R.string.display_type,
            application.applicationContext.getString(
                when(it.isRental) {
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }))
    }

    init {
        _selectedMarsProperty.value = marsProperty
    }


}