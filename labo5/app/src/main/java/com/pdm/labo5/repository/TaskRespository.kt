package com.pdm.labo5.repository

import com.pdm.labo5.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRespository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}