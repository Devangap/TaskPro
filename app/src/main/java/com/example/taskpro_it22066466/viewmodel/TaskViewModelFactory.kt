package com.example.taskpro_it22066466.viewmodel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskpro_it22066466.repository.TaskRepository

class TaskViewModelFactory(val app:Application,private val taskRepository: TaskRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(app, taskRepository)as T
    }
}





