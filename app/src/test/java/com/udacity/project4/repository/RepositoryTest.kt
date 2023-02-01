package com.udacity.project4.repository

import com.udacity.project4.ReminderDataItem
import com.udacity.project4.data.source.FakeDataSource
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


internal class RepositoryTest {
private val database = listOf<ReminderDataItem>(
    ReminderDataItem("title", "descri", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 , "1"),
    ReminderDataItem("titloos", "descrioos","10301 - 101st Street NW - Lot #287", 53.54580885366693,-113.4930744767189 , "2"))
    private lateinit var tasksLocalDataSource: FakeDataSource
    private lateinit var repositoryTest: Repository

    @Before
    fun createRepository() {
        tasksLocalDataSource = FakeDataSource(database)
        // Get a reference to the class under test

    }

    @Test
     fun getAllRecord() = runBlockingTest {
        // When tasks are requested from the tasks repository
        val tasks = tasksLocalDataSource.getReminders() as CustomResult.Success
       assertEquals(database ,tasks.data)
    }

    @Test
    fun getCertainRecord() = runBlockingTest {
        // When tasks are requested from the tasks repository
        val tasks = tasksLocalDataSource.getReminder(database[1].id) as CustomResult.Success
        assertEquals(database[1] ,tasks.data)
    }


    @Test
    fun addRecord() = runBlockingTest {
        // When tasks are requested from the tasks repository
        val newRecord = ReminderDataItem("titless", "descriaa", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 , "3")
        val newList = listOf<ReminderDataItem>(
            ReminderDataItem("title", "descri", "CRAFT Beer Market Edmonton", 53.54210529714351, -113.49129751324654 , "1"),
            ReminderDataItem("titloos", "descrioos","10301 - 101st Street NW - Lot #287", 53.54580885366693,-113.4930744767189 , "2") ,
            newRecord)
        tasksLocalDataSource.saveReminder(newRecord)
        assertEquals(newList ,tasksLocalDataSource.listOfReminder)
    }


    @Test
    fun clearAll() = runBlockingTest {
        // When tasks are requested from the tasks repository
        tasksLocalDataSource.deleteAllReminders()
        assertEquals(listOf<ReminderDataItem>() ,tasksLocalDataSource.listOfReminder)
    }

}