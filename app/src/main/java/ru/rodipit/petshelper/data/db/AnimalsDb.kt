package ru.rodipit.petshelper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.UserEntity

@Database(version = 1, entities = [AnimalEntity::class, UserEntity::class], exportSchema = false)
abstract class AnimalsDb: RoomDatabase() {

    abstract fun getDao(): AnimalDao

    companion object{

        private val instance: AnimalsDb? = null

        fun getInstance(context: Context): AnimalsDb{
            if (instance != null)
                return instance

            return Room.databaseBuilder(
                context,
                AnimalsDb::class.java,
                "Animals"
            ).build()
        }
    }

}