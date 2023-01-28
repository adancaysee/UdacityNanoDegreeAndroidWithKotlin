package com.udacity.gdgfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.udacity.gdgfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(getNavController(), binding.drawerLayout)


    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(getNavController(), binding.drawerLayout)

        binding.navView.setupWithNavController(getNavController())

        getNavController().addOnDestinationChangedListener { _, destination, _ ->
            val toolbar = supportActionBar ?: return@addOnDestinationChangedListener

            when (destination.id) {
                R.id.home_destination -> {
                    toolbar.setDisplayShowTitleEnabled(false)
                    binding.heroImage.visibility = View.VISIBLE
                }
                else -> {
                    toolbar.setDisplayShowTitleEnabled(true)
                    binding.heroImage.visibility = View.GONE
                }
            }
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        return navHostFragment.navController
    }
}

