package com.udacity.project4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.project4.databinding.ActivityReminderDescriptionBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class ReminderDescriptionActivity : AppCompatActivity() , OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityReminderDescriptionBinding
    private lateinit var reminder: ReminderDataItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderDescriptionBinding.inflate(layoutInflater)

        val bundle = intent.extras
         reminder= bundle!!.getSerializable("locationReminder") as ReminderDataItem
        binding.reminderDataItem = reminder


        val mapFragment = supportFragmentManager.findFragmentById(R.id.detailsMap) as SupportMapFragment
        mapFragment.getMapAsync(this)


        setContentView(binding.root)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                binding.root.context,
                R.raw.map_style
            )
        )
        setCurrentLocation(mMap)
    }



    private fun setCurrentLocation(map: GoogleMap) {
        val currentPosition = LatLng(
            reminder.latitude!!,
            reminder.longitude!!
        )
        map.addMarker(MarkerOptions().position(currentPosition).title(reminder.title))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition , 14.0f))
    }

}


