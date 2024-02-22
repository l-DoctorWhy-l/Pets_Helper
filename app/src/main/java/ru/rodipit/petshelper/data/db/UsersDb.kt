package ru.rodipit.petshelper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.entities.UserEntity

@Database(version = 1, entities = [UserEntity::class], exportSchema = false)
abstract class UsersDb: RoomDatabase() {

    abstract fun getDao(): UserDao

    companion object{

        private val instance: UsersDb? = null

        fun getInstance(context: Context): UsersDb{
            if (instance != null)
                return instance

            return Room.databaseBuilder(
                context,
                UsersDb::class.java,
                "Users"
            ).build()
        }
    }

}