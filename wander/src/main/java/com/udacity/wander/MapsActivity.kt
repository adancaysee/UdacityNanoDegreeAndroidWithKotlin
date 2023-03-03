package com.udacity.wander

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.udacity.wander.databinding.ActivityMapsBinding

/**
 * For styling I use cloud map styling
 * https://developers.google.com/maps/documentation/cloud-customization/overview
 */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MenuProvider {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (this as MenuHost).addMenuProvider(this)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        val homeLatLng = LatLng(60.366124, 5.349366)
        map.addMarker(MarkerOptions().position(homeLatLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 15f))

        setMapLongClick(map)
        setOnPoiClick(map)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.map_options_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        map.mapType = when (menuItem.itemId) {
            R.id.hybrid_map -> GoogleMap.MAP_TYPE_HYBRID
            R.id.satellite_map -> GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain_map -> GoogleMap.MAP_TYPE_TERRAIN
            else -> GoogleMap.MAP_TYPE_NORMAL
        }
        return true
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(
                        getString(
                            R.string.lat_long_snippet,
                            latLng.latitude,
                            latLng.longitude
                        )
                    )
            )
        }
    }

    /**
     *  poi = point of interest
     */
    private fun setOnPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val marker = map.addMarker(MarkerOptions().position(poi.latLng).title(poi.name))
            marker?.showInfoWindow()
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi.latLng,15f))


        }
    }
}