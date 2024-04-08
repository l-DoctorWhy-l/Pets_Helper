package ru.rodipit.petshelper.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.dao.TaskDao
import ru.rodipit.petshelper.data.entities.Task
import java.util.Calendar

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun addTask(task: Task){
        taskDao.insert(task)
    }

    suspend fun loadTask(id: Int): Task {
        return taskDao.getTask(id)
    }

    suspend fun loadTasks(animalId: Int): MutableList<Task> {
        return taskDao.getTasks(animalId).toMutableList()
    }

    suspend fun loadTasks(animalId: Int, type: Int): MutableList<Task>{
        return taskDao.getTasks(animalId, type).toMutableList()
    }

    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.update(task)
    }

    suspend fun loadCurrentDateTasks(animalId: Int): MutableList<Task>{
        val tasks = loadTasks(animalId)
        println("TASK SIZE: ${tasks.size}")
        val nowTime = Calendar.getInstance()
        val result = mutableListOf<Task>()
        tasks.forEach{ task ->
            if(task.repeating == Task.DAILY)
                result.add(task)
            else{
                val taskDateTime = task.date.split("/").map { it.toInt() } + task.time.split(":").map { it.toInt() }
                val taskTime = Calendar.getInstance().apply {
                    set(Calendar.YEAR, taskDateTime[0])
                    set(Calendar.MONTH, taskDateTime[1] - 1)
                    set(Calendar.DAY_OF_MONTH, taskDateTime[2])
                    set(Calendar.HOUR_OF_DAY, taskDateTime[3])
                    set(Calendar.MINUTE, taskDateTime[4])
                }
                if(
                    taskTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR)
                    && taskTime.get(Calendar.DAY_OF_YEAR) == nowTime.get(Calendar.DAY_OF_YEAR)
                    || task.repeating == Task.WEEKLY && taskTime.get(Calendar.DAY_OF_WEEK) == nowTime.get(Calendar.DAY_OF_WEEK)
                    || task.repeating == Task.YEARLY && taskTime.get(Calendar.DAY_OF_YEAR) == nowTime.get(Calendar.DAY_OF_YEAR)
                    || task.repeating == Task.MONTHLY && taskTime.get(Calendar.DAY_OF_MONTH) == nowTime.get(Calendar.DAY_OF_MONTH)
                    || task.repeating == Task.HALF_YEAR && taskTime.get(Calendar.DAY_OF_MONTH) == nowTime.get(Calendar.DAY_OF_MONTH)
                    && taskTime.get(Calendar.MONTH) % 6 == nowTime.get(Calendar.MONTH) % 6
                    ) {
                    result.add(task)
                }
            }
        }

        return result
    }
}