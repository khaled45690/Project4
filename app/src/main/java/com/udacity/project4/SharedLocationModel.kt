package com.udacity.project4

import android.location.Location
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.udacity.project4.database.LocationReminderDatabase
import com.udacity.project4.repository.Repository
import com.udacity.project4.views.save_reminder.SaveReminderFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.repository.CustomResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SharedLocationModel : ViewModel() {
    var isLoadingLocation: Boolean = true
    var newReminder : ReminderDataItem = ReminderDataItem()
    var currentLocation: Location? = null
    var listOfReminder : MutableLiveData<List<ReminderDataItem>> = MutableLiveData<List<ReminderDataItem>>().apply {
        value = listOf()
    }
    fun goToMapFragment(view : View){
        if(isLoadingLocation){
            Snackbar.make(view, "please wait till your current location loads", Snackbar.LENGTH_LONG).show()
        }else{
            view.findNavController().navigate(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
        }
      }

    fun selectLocation(view : View){
//        Point of Interest
        if(newReminder.location.isNullOrBlank()){
            Snackbar.make(view, "please select one of Point of Interest", Snackbar.LENGTH_LONG).show()
        }else{
            view.findNavController().navigateUp()
        }
    }
    fun saveNewReminder(view : View){
        if(isNullCheck(newReminder)){
            val dataSource = LocationReminderDatabase.getInstance(view.context).LocationReminderDatabaseDao

            val job: Job = Job()
            val uiScope = CoroutineScope( job)
            uiScope.launch {
                Repository(dataSource).saveReminder(newReminder)
                newReminder = ReminderDataItem()
            }
            view.findNavController().navigateUp()
            Snackbar.make(view, "record saved successfully!", Snackbar.LENGTH_LONG).show()
        }else{
            Snackbar.make(view, "please make sure you selected a location and wrote a title and description", Snackbar.LENGTH_LONG).show()
        }

    }
    private fun isNullCheck(Reminder : ReminderDataItem) : Boolean{
        if(Reminder.location.isNullOrBlank()||Reminder.title.isNullOrBlank()||Reminder.description.isNullOrBlank()|| Reminder.latitude == null || Reminder.longitude == null){
            return false
        }
        return true
    }

     fun getReminders(view : View){
        val dataSource = LocationReminderDatabase.getInstance(view.context).LocationReminderDatabaseDao
        val job: Job = Job()
        val uiScope = CoroutineScope( job)
        uiScope.launch {
            val result = Repository(dataSource).getReminders()
            if (result is CustomResult.Success<List<ReminderDataItem>>){
                println(result.data)
                listOfReminder.postValue(result.data)

            }else if(result is CustomResult.Error){
                Snackbar.make(view, result.message!!, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}