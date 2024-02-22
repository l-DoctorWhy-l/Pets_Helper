package ru.rodipit.petshelper.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.rodipit.petshelper.data.entities.UserEntity


@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity
}