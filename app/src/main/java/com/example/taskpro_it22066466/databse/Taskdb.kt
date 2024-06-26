package com.example.taskpro_it22066466.databse


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskpro_it22066466.model.Task

@Database(entities = [Task::class], version = 1)
abstract class Taskdb : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: Taskdb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                Taskdb::class.java,
                "task_database"
            ).build()
    }
}
