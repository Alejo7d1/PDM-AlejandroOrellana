package com.pdm.labo5.data

import android.content.Context
import com.pdm.labo5.database.AppDatabase
import com.pdm.labo5.repository.TaskRepositoryImpl
import com.pdm.labo5.repository.TaskRespository

class AppProvider(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)
    private val taskDao = appDatabase.taskDao()

    private val taskRespository: TaskRespository =
        TaskRepositoryImpl(taskDao)

    fun provideTaskRepository(): TaskRespository{
        return taskRespository
    }
}