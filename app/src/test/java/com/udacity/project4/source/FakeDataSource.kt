package com.udacity.project4.data.source

import com.udacity.project4.ReminderDataItem
import com.udacity.project4.repository.CustomResult
import com.udacity.project4.repository.ReminderDataSource

class FakeDataSource(listOfReminderParameter : List<ReminderDataItem> = listOf<ReminderDataItem>()) : ReminderDataSource{
    var listOfReminder =  listOfReminderParameter
    override suspend fun getReminders(): CustomResult<List<ReminderDataItem>> {
        return if(listOfReminder.isNotEmpty()){
            CustomResult.Success(listOfReminder)
        }else{
            CustomResult.Error(
                Exception("Tasks not found").toString())
        }
    }

    override suspend fun saveReminder(reminder: ReminderDataItem) {
        listOfReminder = listOfReminder.plus(reminder)
    }

    override suspend fun getReminder(id: String): CustomResult<ReminderDataItem> {
       val item : ReminderDataItem? =  listOfReminder.find {
            it.id == id
        }
        println(item)
        return if(item != null){
            CustomResult.Success(item)
        }else{
            CustomResult.Error(
                Exception("Tasks not found with this id").toString())
        }
    }

    override suspend fun deleteAllReminders() {
        listOfReminder = listOf()
    }


}