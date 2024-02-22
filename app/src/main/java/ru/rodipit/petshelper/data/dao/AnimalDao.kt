package ru.rodipit.petshelper.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.rodipit.petshelper.data.entities.AnimalEntity


@Dao
interface AnimalDao {

    @Insert
    suspend fun insert(animal: AnimalEntity)

    @Delete
    suspend fun delete(animal: AnimalEntity)

    @Update
    suspend fun update(animal: AnimalEntity)

    @Query("SELECT * FROM Animals WHERE id = :id")
    suspend fun getUser(id: Int): AnimalEntity

    @Query("SELECT * FROM Animals WHERE userId = :userId")
    suspend fun getAnimals(userId: Int): List<AnimalEntity>

    @Query("SELECT * FROM Animals WHERE id = :animalId")
    suspend fun getAnimal(animalId: Int): AnimalEntity

}