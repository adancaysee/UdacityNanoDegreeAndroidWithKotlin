package com.udacity.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.udacity.trivia.databinding.ActivityMainBinding

/**
 * Step 1: NavigationUI.setupActionBarWithNavController(this, navController) -- back stack manipulation
           Show up button on action bar
           Do not show in first destination (up button - back button difference)
 * Step 2: getNavController().navigateUp() --  trigger when up button pressed
 * Step 3: NavigationUI.setupActionBarWithNavController(this, navController,drawerLayout) -- add menu to action bar
 * Step 4: NavigationUI.navigateUp(getNavController(),drawerLayout) -- trigger when up and menu button pressed
 * Step 5: binding.navView.setupWithNavController(navController) -- for drawer menu items navigation
 * You can use appbar configuration

 */

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = getNavController()

        NavigationUI.setupActionBarWithNavController(this, navController,drawerLayout)
        binding.navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        //return getNavController().navigateUp()
        //return NavigationUI.navigateUp(getNavController(),drawerLayout)
        return NavigationUI.navigateUp(getNavController(),appBarConfiguration)
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        return navHostFragment.navController
    }
}



