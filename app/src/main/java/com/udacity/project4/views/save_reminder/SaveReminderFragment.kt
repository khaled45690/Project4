package com.udacity.project4.views.save_reminder

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.project4.SharedLocationModel
import com.udacity.project4.databinding.FragmentSaveReminderBinding


class SaveReminderFragment : Fragment() , android.location.LocationListener{
    private lateinit var locationManager : LocationManager
    private lateinit var binder: FragmentSaveReminderBinding
    private lateinit var sharedViewModel: SharedLocationModel

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedLocationModel::class.java]
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10000F, this)
        binder = FragmentSaveReminderBinding.inflate(inflater, container, false)
        binder.sharedViewModel = sharedViewModel
        binder.view = binder.root
        return binder.root
    }

    override fun onLocationChanged(location: Location) {
        println(location.latitude)
        sharedViewModel.currentLocation = location
        sharedViewModel.isLoadingLocation = false
        binder.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        locationManager.removeUpdates(this)
    }
}