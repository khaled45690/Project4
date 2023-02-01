package com.udacity.project4.views.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.project4.R
import com.udacity.project4.ReminderDataItem
import com.udacity.project4.SharedLocationModel
import com.udacity.project4.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment() , OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binder: FragmentMapsBinding
    private lateinit var sharedViewModel: SharedLocationModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedLocationModel::class.java]

        binder = FragmentMapsBinding.inflate(inflater, container, false)

        binder.sharedViewModel = sharedViewModel
        binder.viewParameter = binder.root
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binder.getCurrentLocationButton.setOnClickListener {
            setCurrentLocation(mMap)
        }
        setHasOptionsMenu(true)
        binder.myToolbar.setOnMenuItemClickListener{

            when (it.itemId) {
                // Change the map type based on the user's selection.
                R.id.dark_customized_map -> {
                    println("dasda")
                    mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                    true
                }
                R.id.hybrid_map -> {
                    println("dasda")
                    mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                    true
                }
                R.id.satellite_map -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    true
                }
                R.id.terrain_map -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    true
                }
                else -> true
            }
        }

        binder.setPOIForTesting.setOnClickListener{
            autoMarkerSetter(mMap)
        }
        return binder.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(),
                R.raw.map_style
            )
        )
        setCurrentLocation(mMap)
        setMapClick(mMap)
    }

    private fun setMapClick(map: GoogleMap) {

        map.setOnPoiClickListener { poi ->
            sharedViewModel.newReminder.location = poi.name
            sharedViewModel.newReminder.latitude = poi.latLng.latitude
            sharedViewModel.newReminder.longitude = poi.latLng.longitude

            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
                    .rotation(3.0f)
            )
        }
    }


    private fun setCurrentLocation(map: GoogleMap) {
        val currentPosition = LatLng(
            sharedViewModel.currentLocation!!.latitude,
            sharedViewModel.currentLocation!!.longitude
        )
        map.addMarker(MarkerOptions().position(currentPosition).title("your current position"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition , 15.0f))
    }
    private fun autoMarkerSetter(map: GoogleMap){
        val task =  ReminderDataItem("title", "descri", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 )
        sharedViewModel.newReminder.location = task.location
        sharedViewModel.newReminder.latitude = task.latitude
        sharedViewModel.newReminder.longitude = task.longitude
        val latlng = LatLng(task.latitude!! , task.longitude!!)
        map.clear()
        map.addMarker(
            MarkerOptions()
                .position(latlng)
                .title(task.location)
        )
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng , 15.0f))
    }

}


