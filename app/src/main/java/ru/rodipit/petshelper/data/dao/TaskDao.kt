package ru.rodipit.petshelper.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.rodipit.petshelper.data.entities.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM Tasks WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Query("SELECT * FROM Tasks WHERE animalId = :animalId ORDER BY time ASC")
    suspend fun getTasks(animalId: Int): List<Task>

    @Query("SELECT * FROM Tasks WHERE animalId = :animalId and type = :type ORDER BY time ASC")
    suspend fun getTasks(animalId: Int, type: Int): List<Task>
}