package com.udacity.treasurehunt.ui

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.material.snackbar.Snackbar
import com.udacity.treasurehunt.BuildConfig
import com.udacity.treasurehunt.R
import com.udacity.treasurehunt.databinding.ActivityMainBinding
import com.udacity.treasurehunt.util.GeofencingConstants
import com.udacity.treasurehunt.util.hasPermission
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: GeofenceViewModel by lazy {
        ViewModelProvider(
            this,
            SavedStateViewModelFactory(this.application, this)
        )[GeofenceViewModel::class.java]
    }

    private val resolutionForResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            viewModel.addGeofenceForClue()
        } else {
            showLocationSettingSnackbar()
        }

    }

    private val requestPermissionLauncher = registerForActivityResult(
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
                showOpenPermissionSettingSnackbar()
            }
            permissions[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == true -> {
                viewModel.checkDeviceLocationSettingsAndStartGeofence()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.checkLocationSettingFailureEvent.observe(this) {
            it?.let { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        resolutionForResultLauncher.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Timber.d("Error geting location settings resolution: " + sendEx.message)
                    }
                } else {
                    showLocationSettingSnackbar()
                }
                viewModel.doneCheckLocationFailureEvent()
            }
        }

        viewModel.addGeofenceForClueEvent.observe(this) {
            it?.let {
                val message = if (it) {
                    application.getString(R.string.geofences_added)
                } else {
                    application.getString(R.string.geofences_not_added)
                }
                Toast.makeText(application.applicationContext, message, Toast.LENGTH_LONG).show()
                viewModel.doneAddGeofenceForClueEvent()
            }
        }

        viewModel.removeAllGeofencesEvent.observe(this) {
            it?.let {
                val message = if (it) {
                    application.getString(R.string.geofences_removed)
                } else {
                    application.getString(R.string.geofences_not_removed)
                }
                Toast.makeText(application.applicationContext, message, Toast.LENGTH_LONG).show()
                viewModel.doneRemoveAllGeofencesEvent()
            }
        }

        val extras = intent?.extras
        if (extras != null) {
            if (extras.containsKey(GeofencingConstants.EXTRA_GEOFENCE_INDEX)) {
                viewModel.updateHint(extras.getInt(GeofencingConstants.EXTRA_GEOFENCE_INDEX))
                viewModel.checkDeviceLocationSettingsAndStartGeofence()
            }
        }

    }


    override fun onStart() {
        super.onStart()
        requestForegroundLocationPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            viewModel.removeGeofences()
        }
    }

    //permissions
    private fun requestForegroundLocationPermissions() {
        when {
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) || hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                requestBackgroundLocationPermission()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                showOpenPermissionSettingSnackbar()
            }
            else -> {
                requestPermissionLauncher.launch(
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
                viewModel.checkDeviceLocationSettingsAndStartGeofence()
                requestNotificationPermission()
            }
            hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
                viewModel.checkDeviceLocationSettingsAndStartGeofence()
                requestNotificationPermission()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
                showOpenPermissionSettingSnackbar()
            }
            else -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasPermission(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    private fun showOpenPermissionSettingSnackbar() {
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

    private fun showLocationSettingSnackbar() {
        Snackbar.make(
            binding.activityMapsMain,
            R.string.location_required_error,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.ok) {
                viewModel.checkDeviceLocationSettingsAndStartGeofence()
            }
        }.show()
    }
}



