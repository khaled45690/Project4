package com.udacity.project4.views.main

import android.Manifest
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.udacity.project4.SharedLocationModel
import com.udacity.project4.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var sharedViewModel: SharedLocationModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION), 1);
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedLocationModel::class.java]
        val binder = FragmentMainBinding.inflate(inflater, container, false)
        binder.lifecycleOwner = this
        binder.viewParameter = binder.root
        binder.viewModel = viewModel
        sharedViewModel.getReminders(binder.root)


        val adapter = RemindersListAdapter()
        adapter.data = sharedViewModel.listOfReminder.value!!
        binder.remindersRecycler.adapter = adapter

        sharedViewModel.listOfReminder.observe(viewLifecycleOwner , Observer {
            adapter.data = it
            if(it.isNotEmpty()){
                binder.noDataTextView.visibility = View.GONE
                binder.noDataImageView.visibility = View.GONE
            }
        })


        return binder.root
    }


}