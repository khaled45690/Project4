package com.udacity.project4.views.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.udacity.project4.AuthActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {


    fun logout(view : View){
        FirebaseAuth.getInstance().signOut()
        val intent : Intent = Intent(view.context, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(view.context , intent , null)
    }

    fun addNewLocation(view : View){
        if (ActivityCompat.checkSelfPermission(
                view.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                view.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Snackbar.make(view, "please go to settings and allow location access", Snackbar.LENGTH_LONG).show();

        }else{
            view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToSaveReminderFragment())
        }
    }




}