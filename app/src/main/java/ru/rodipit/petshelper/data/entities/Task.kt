package ru.rodipit.petshelper.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Int? = null, var animalId: Int, var title: String, var description: String, var time: Long, var repeating: Int, var type: Int, var state: Boolean = false){







    companion object{
        const val ONE_TIME = 1
        const val DAILY = 2
        const val WEEKLY = 3
        const val MONTHLY = 4
        const val HALF_YEAR = 5
        const val YEARLY = 6
        const val HEALTH = 7
        const val EATING = 8
        const val OTHER = 9
    }
}
