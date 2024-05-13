package com.example.pokegnomego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val back = findViewById<Button>(R.id.button_back)
        back.setOnClickListener {
            val i = Intent(this@MapActivity, MainActivity::class.java)

            startActivity(i)

            finish()
        }

    }

    override fun onMapReady(MyMap: GoogleMap) {
        val latLng = LatLng(51.11054553520132, 17.03326405469526)
        MyMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

    }
}