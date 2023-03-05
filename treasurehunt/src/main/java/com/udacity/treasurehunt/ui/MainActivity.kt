package com.udacity.treasurehunt.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.udacity.treasurehunt.BuildConfig
import com.udacity.treasurehunt.R
import com.udacity.treasurehunt.databinding.ActivityMainBinding
import com.udacity.treasurehunt.util.hasPermission

/**
 * Foreground Permissions
 * ACCESS_FINE_LOCATION --> precise location
 * ACCESS_COARSE_LOCATION --> approximate location
 * On Api 31 and higher --> permission pop-up shows both of them
 *
 * Background Permission
 * For Api 29 and higher
 * Firstly you must get permissions for foreground permissions
 * On Api 29(Android 10) --> Showing permission dialog
 * On Api 30(Android 11) and higher --> If request a foreground location permission and the background
 *                                      location permission at the same time, the system ignores the request
 *                                  --> Showing permission screen
 */

/**
 * Request Permission Steps
 * 1. Add permission to manifest
 * 2. Register activity for request permission
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                requestBackgroundLocationPermission()
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == false ||
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION] == false ||
                    permissions[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == false -> {
                showSnackbar()
            }
            permissions[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == true -> {
                checkDeviceLocationSettingsAndStartGeofence()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        requestForegroundLocationPermissions()
    }

    private fun requestForegroundLocationPermissions() {
        when {
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) || hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                requestBackgroundLocationPermission()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                showSnackbar()
            }
            else -> {
                requestLocationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    )
                )
            }
        }
    }

    private fun requestBackgroundLocationPermission() {
        when {
            (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) -> {
                checkDeviceLocationSettingsAndStartGeofence()
            }
            hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
                checkDeviceLocationSettingsAndStartGeofence()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
                showSnackbar()
            }
            else -> {
                requestLocationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            }
        }
    }

    private fun checkDeviceLocationSettingsAndStartGeofence() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        Toast.makeText(this, "Ready", Toast.LENGTH_LONG).show()
    }

    private fun showSnackbar() {
        Snackbar.make(
            binding.activityMapsMain,
            R.string.permission_denied_explanation,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.settings) {
                startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }.show()
    }

}



