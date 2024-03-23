package ru.rodipit.petshelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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

    suspend fun updateTask(task: Task){
        taskDao.update(task)
    }

    suspend fun loadCurrentDateTasks(animalId: Int): MutableList<Task>{
        val tasks = loadTasks(animalId)
        println(tasks.size)
        val nowTime = Calendar.getInstance()
        val result = mutableListOf<Task>()
        tasks.forEach{
            val taskTime = Calendar.getInstance().apply { timeInMillis = it.time }
            if(it.repeating == Task.DAILY ||
                taskTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR) && taskTime.get(Calendar.DAY_OF_YEAR) == nowTime.get(Calendar.DAY_OF_YEAR)
                || it.repeating == Task.WEEKLY && taskTime.get(Calendar.DAY_OF_WEEK) == nowTime.get(Calendar.DAY_OF_WEEK)
                || it.repeating == Task.YEARLY && taskTime.get(Calendar.DAY_OF_YEAR) == nowTime.get(Calendar.DAY_OF_YEAR)
                || it.repeating == Task.MONTHLY && taskTime.get(Calendar.DAY_OF_MONTH) == nowTime.get(Calendar.DAY_OF_MONTH)
                || it.repeating == Task.HALF_YEAR && taskTime.get(Calendar.DAY_OF_MONTH) == nowTime.get(Calendar.DAY_OF_MONTH)
                && taskTime.get(Calendar.MONTH) % 6 == nowTime.get(Calendar.MONTH) % 6){
                result.add(it)
            }
        }
        return result
    }
}