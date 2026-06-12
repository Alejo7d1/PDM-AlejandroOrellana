package com.pdm.labo5.repository

import com.pdm.labo5.database.dao.TaskDao
import com.pdm.labo5.database.entities.toEntity
import com.pdm.labo5.database.entities.toModel
import com.pdm.labo5.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRespository{

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map {it.toModel()}
        }
    }

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }

}