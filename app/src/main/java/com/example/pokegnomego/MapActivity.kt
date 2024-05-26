package com.example.pokegnomego

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val permissionCode = 101
    private lateinit var WroclawMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val back = findViewById<Button>(R.id.button_back)
        back.setOnClickListener {
            val i = Intent(this@MapActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }


        val photoButton = findViewById<Button>(R.id.button_photo)
        photoButton.setOnClickListener {

        }

        locationRequest = LocationRequest.create().apply {
            interval = 1000 // Request location update every 10 seconds
            fastestInterval = 500 // The fastest interval for location updates, 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        //update the location after obtained
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.d("update", location.latitude.toString())
                    val latLng = LatLng(location.latitude, location.longitude)
                    WroclawMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                    WroclawMap.addMarker(MarkerOptions().position(latLng))
                }
            }
        }

    }

    override fun onMapReady(MyMap: GoogleMap) {
        WroclawMap = MyMap
        val latLng = LatLng(51.11054553520132, 17.03326405469526)
        WroclawMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

}