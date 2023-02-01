package com.udacity.project4.repository

import com.udacity.project4.ReminderDataItem


/**
 * Main entry point for accessing reminders data.
 */
interface ReminderDataSource {
    suspend fun getReminders(): CustomResult<List<ReminderDataItem>>
    suspend fun saveReminder(reminder: ReminderDataItem)
    suspend fun getReminder(id: String): CustomResult<ReminderDataItem>
    suspend fun deleteAllReminders()
}