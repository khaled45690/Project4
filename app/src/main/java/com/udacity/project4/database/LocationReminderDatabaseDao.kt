/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity.project4.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.project4.ReminderDataItem
import java.util.*

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface LocationReminderDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationReminder: ReminderDataItem)

    @Query("SELECT * from location_reminder WHERE id == :locationReminderId")
    suspend fun getReminderById(locationReminderId: String) : ReminderDataItem?


    @Query("SELECT * from location_reminder")
    suspend fun getAllRecords(): List<ReminderDataItem>

//    @Query("SELECT * from location_reminder  WHERE closeApproachDate > :currentDate AND closeApproachDate < :lastDate ORDER BY closeApproachDate ASC")
//    suspend fun getWeekRecords(currentDate: String ,lastDate: String): List<ReminderDataItem>
//
//    @Query("SELECT * from location_reminder WHERE closeApproachDate == :currentDate ORDER BY closeApproachDate ASC")
//    suspend fun getTodayRecords(currentDate: String): List<ReminderDataItem>
//
    @Query("DELETE from location_reminder")
    suspend fun clear()


}

