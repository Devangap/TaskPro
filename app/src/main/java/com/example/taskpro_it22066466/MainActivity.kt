package com.example.taskpro_it22066466

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taskpro_it22066466.databse.Taskdb
import com.example.taskpro_it22066466.repository.TaskRepository
import com.example.taskpro_it22066466.viewmodel.TaskViewModel
import com.example.taskpro_it22066466.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        val noteRepository = TaskRepository(Taskdb(this))
        val viewModelProviderFactory = TaskViewModelFactory(application, noteRepository)
        taskViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TaskViewModel::class.java)
    }
}