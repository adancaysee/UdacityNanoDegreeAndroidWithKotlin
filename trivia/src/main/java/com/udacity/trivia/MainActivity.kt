package com.udacity.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.udacity.trivia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = findNavController()
        navController?.let {
            NavigationUI.setupActionBarWithNavController(this, it)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }


    private fun findNavController(): NavController? {
        val navHostFragment =
            this.supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as? NavHostFragment
        return navHostFragment?.navController
    }
}
