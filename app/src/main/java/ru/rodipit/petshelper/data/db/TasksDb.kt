package ru.rodipit.petshelper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.rodipit.petshelper.data.dao.TaskDao
import ru.rodipit.petshelper.data.entities.Task

@Database(version = 1, entities = [Task::class], exportSchema = false)
abstract class TasksDb: RoomDatabase() {

    abstract fun getDao(): TaskDao

    companion object{

        private val instance: TasksDb? = null

        fun getInstance(context: Context): TasksDb{
            if (instance != null)
                return instance

            return Room.databaseBuilder(
                context,
                TasksDb::class.java,
                "Tasks"
            ).build()
        }
    }

}