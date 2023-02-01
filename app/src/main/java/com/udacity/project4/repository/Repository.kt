package com.udacity.project4.repository

import android.app.Application
import androidx.room.Room
import com.udacity.project4.ReminderDataItem
import com.udacity.project4.database.LocationReminderDatabase
import com.udacity.project4.database.LocationReminderDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class Repository(private val database: LocationReminderDatabaseDao) : ReminderDataSource {

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getRepository(app: Application): Repository {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(app,
                    LocationReminderDatabase::class.java, "Tasks.db")
                    .build()
                Repository(database.LocationReminderDatabaseDao).also {
                    INSTANCE = it
                }
            }
        }
    }

    override suspend fun getReminders(): CustomResult<List<ReminderDataItem>> {
       return withContext(Dispatchers.IO) {
            return@withContext try {
                CustomResult.Success(database.getAllRecords())
            } catch (ex: Exception) {
                CustomResult.Error(ex.localizedMessage)
            }
        }
    }

    override suspend fun saveReminder(reminder: ReminderDataItem) =
        withContext(Dispatchers.IO) {
            database.insert(reminder)
        }

    override suspend fun getReminder(id: String): CustomResult<ReminderDataItem>  = withContext(Dispatchers.IO) {
        try {
            val reminder = database.getReminderById(id)
            if (reminder != null) {
                return@withContext CustomResult.Success(reminder)
            } else {
                return@withContext  CustomResult.Error("Reminder not found!")
            }
        } catch (e: Exception) {
            return@withContext  CustomResult.Error(e.localizedMessage)
        }
    }

    override suspend fun deleteAllReminders() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }


}