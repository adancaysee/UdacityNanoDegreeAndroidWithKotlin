package com.udacity.dessertpusher

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import com.udacity.dessertpusher.databinding.ActivityMainBinding
import timber.log.Timber

/**
 * this.lifecycle == android lifecycle object
 * LifecycleOwner == has a lifecycle --> activities and fragment (these has their own lifecycle object)
 * LifecycleObserver == observe LifecycleOwner
 */

class MainActivity : AppCompatActivity(), MenuProvider {


    private lateinit var binding: ActivityMainBinding
    private var revenue = 0
    private var dessertsSold = 0

    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    private lateinit var dessertTimer: DessertTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate Called")
        this.addMenuProvider(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        dessertTimer = DessertTimer(this.lifecycle)

        binding.dessertButton.setImageResource(currentDessert.imageId)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }



    }

    private fun onDessertClicked() {

        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Show the next dessert
        showCurrentDessert()
    }

    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            else break
        }

        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart Called")
        Timber.i("state : ${this.lifecycle.currentState.name}")
    }


    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
        Timber.i("state : ${this.lifecycle.currentState.name}")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
        Timber.i("state : ${this.lifecycle.currentState.name}")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
        Timber.i("state : ${this.lifecycle.currentState.name}")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop Called")
        Timber.i("state : ${this.lifecycle.currentState.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
        Timber.i("state : ${this.lifecycle.currentState.name}")
    }




    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        try {
            startActivity(getShareIntent())
        }catch (ex:ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
        return true
    }

    private fun getShareIntent(): Intent {
        return ShareCompat.IntentBuilder(this)
            .setType("text/plain")
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .intent
    }
}