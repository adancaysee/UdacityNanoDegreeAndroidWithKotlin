package com.udacity.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.udacity.todo.databinding.ActivityMainBinding
import com.udacity.todo.util.MenuProviderViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val menuProviderViewModel by viewModels<MenuProviderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = getNavController()
        appBarConfiguration = AppBarConfiguration(setOf(R.id.list_task_destination,R.id.statics_destination), binding.drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.drawerLayout.setDrawerTitle(0,"hello")

        menuProviderViewModel.selectDrawerMenu.observe(this) {
            if (it == true) {
                getNavController().navigateUp(appBarConfiguration)
                menuProviderViewModel.doneSelection()
            }
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp(appBarConfiguration)
    }
}