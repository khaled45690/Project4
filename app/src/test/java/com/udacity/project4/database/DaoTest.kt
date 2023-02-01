package com.udacity.project4.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith

    @ExperimentalCoroutinesApi
    @RunWith(AndroidJUnit4::class)
//    @RunWith(RobolectricTestRunner::class)
    @LargeTest
    class DaoTest {

        // Executes each task synchronously using Architecture Components.
        @get:Rule
        var instantExecutorRule = InstantTaskExecutorRule()


        private lateinit var locationReminderDatabase: LocationReminderDatabase

        @Before
        fun initDb() {
            // Using an in-memory database so that the information stored here disappears when the
            // process is killed.
            locationReminderDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                LocationReminderDatabase::class.java
            ).allowMainThreadQueries().build()
        }


        @Test
        fun insertTaskAndGetById() = runBlockingTest {
            // GIVEN - Insert a task.
            val database = locationReminderDatabase.LocationReminderDatabaseDao
            val task1 =  ReminderDataItem("title", "descri", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 )
            val task2 = ReminderDataItem("titloos", "descrioos","10301 - 101st Street NW - Lot #287", 53.54580885366693,-113.4930744767189)
            database.insert(task1)
            database.insert(task2)

            val loaded = database.getReminderById(task1.id)

            assertEquals(loaded!!.id, task1.id)
        }


        @Test
        fun getAllData() = runBlockingTest {
            // GIVEN - Insert a task.
            val database = locationReminderDatabase.LocationReminderDatabaseDao

            val task1 =  ReminderDataItem("title", "descri", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 )
            val task2 = ReminderDataItem("titloos", "descrioos","10301 - 101st Street NW - Lot #287", 53.54580885366693,-113.4930744767189)
            val databaseExpected = listOf(task1 ,task2)
            database.insert(task1)
            database.insert(task2)

            val loaded = database.getAllRecords()

            assertEquals(databaseExpected, loaded)
        }


        @Test
        fun clear() = runBlockingTest {
            // GIVEN - Insert a task.
            val database = locationReminderDatabase.LocationReminderDatabaseDao

            val task1 =  ReminderDataItem("title", "descri", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 )
            val task2 = ReminderDataItem("titloos", "descrioos","10301 - 101st Street NW - Lot #287", 53.54580885366693,-113.4930744767189)
            val databaseExpected = listOf<ReminderDataItem>()
            database.insert(task1)
            database.insert(task2)
            database.clear()
            val loaded = database.getAllRecords()

            assertEquals(databaseExpected, loaded)
        }



        @After
        fun closeDb() {
            locationReminderDatabase.close()
        }


    }