package ru.rodipit.petshelper.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar


@Entity(tableName = "Animals")
data class AnimalEntity(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                        var fullName: String,
                        var name: String,
                        var birthday: Long,
                        var breed: String,
                        var userId: Int?,
                        var type: Int)
{


    constructor() : this(-1, "", "", 0L, "", -1, -1)


    fun getAge(): Int{

        val now = Calendar.getInstance()

        val age = Calendar.getInstance()
        age.timeInMillis = birthday
        if(now.get(Calendar.DAY_OF_YEAR) < age.get(Calendar.DAY_OF_YEAR) )
            return now.get(Calendar.YEAR) - age.get(Calendar.YEAR) - 1

        return now.get(Calendar.YEAR) - age.get(Calendar.YEAR)
    }

    fun getBDay(): String{
        val formattedBDay = StringBuffer()
        val bDayCalendar = Calendar.getInstance().apply { timeInMillis = birthday }
        formattedBDay.append(bDayCalendar.get(Calendar.DAY_OF_MONTH))
        formattedBDay.append("/")
        formattedBDay.append(bDayCalendar.get(Calendar.MONTH) + 1)
        formattedBDay.append("/")
        formattedBDay.append(bDayCalendar.get(Calendar.YEAR))
        return formattedBDay.toString()
    }

}