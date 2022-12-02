package com.udacity.dessertpusher

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import com.udacity.dessertpusher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MenuProvider {


    private lateinit var binding: ActivityMainBinding

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"onCreate Called")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        this.addMenuProvider(this)

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"onStart Called")
    }


    override fun onRestart() {
        super.onRestart()
        Log.i(TAG,"onRestart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG,"onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG,"onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy Called")
    }

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
            .setText("test")
            .intent
    }
}