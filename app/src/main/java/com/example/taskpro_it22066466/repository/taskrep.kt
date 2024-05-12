package com.example.taskpro_it22066466.repository

import com.example.taskpro_it22066466.databse.Taskdb
import com.example.taskpro_it22066466.model.Task

class TaskRepository(private val db: Taskdb) {
    suspend fun insertTask(task: Task) = db.getTaskDao().insertTask(task)

    suspend fun deleteTask(task: Task) = db.getTaskDao().deleteTask(task)

    suspend fun updateTask(task: Task) = db.getTaskDao().updateTask(task)

    fun getAllTasks() = db.getTaskDao().getAllTasks()

    fun searchTask(query: String?) = db.getTaskDao().searchTask(query)
}