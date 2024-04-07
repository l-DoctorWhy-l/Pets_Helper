package ru.rodipit.petshelper.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.core.Tools
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.math.abs

@Entity(tableName = "Tasks")
data class Task(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                var animalId: Int,
                var title: String,
                var description: String,
                var date: String,
                var time: String,
                var lastCompleteTime: Long,
                var repeating: Int,
                var type: Int,
                var state: Boolean = false) : Comparable<Task>{



    fun doTask(){
        lastCompleteTime = System.currentTimeMillis()
        state = true
    }
    fun undoTask(){
        lastCompleteTime = 0
        state = false
    }
//    fun resetState(currentTime: Long){
//        val currentDate = GregorianCalendar.getInstance().apply{ timeInMillis = currentTime }
//        val lastCompleteDate = GregorianCalendar.getInstance().apply{ timeInMillis = lastCompleteTime}
//        if(!state){
//            this.lastCompleteTime = 0
//            return
//        }
//        if(repeating == DAILY && currentDate[Calendar.DAY_OF_YEAR] != lastCompleteDate[Calendar.DAY_OF_YEAR]){
//            lastCompleteTime = 0
//            state = false
//            return
//        }
//        if(repeating == WEEKLY && currentDate[Calendar.DAY_OF_YEAR] - lastCompleteDate[Calendar.DAY_OF_YEAR] >= 7){
//            lastCompleteTime = 0
//            state = false
//            return
//        }
//        if(repeating == MONTHLY && currentDate[Calendar.DAY_OF_YEAR] - lastCompleteDate[Calendar.DAY_OF_YEAR] >= 29){
//            lastCompleteTime = 0
//            state = false
//            return
//        }
//        if(repeating == YEARLY && currentDate[Calendar.YEAR] != lastCompleteDate[Calendar.YEAR]){
//            lastCompleteTime = 0
//            state = false
//            return
//        }
//        if(repeating == HALF_YEAR &&
//            abs(currentDate[Calendar.MONTH] - lastCompleteDate[Calendar.MONTH]) >= 6){
//            lastCompleteTime = 0
//            state = false
//            return
//        }
//    }

    fun getTaskTypeResourceId(): Int{
        return when(type){
            HEALTH -> R.drawable.baseline_healing_24
            EATING -> R.drawable.baseline_lunch_dining_24
            OTHER -> R.drawable.baseline_pets_24
            else -> R.drawable.baseline_pets_24
        }
    }





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

    override fun compareTo(other: Task): Int {
        if(Tools.convertDateToLong(this.date) > Tools.convertDateToLong(other.date))
            return 1
        if(Tools.convertDateToLong(this.date) < Tools.convertDateToLong(other.date))
            return -1
        if(Tools.convertTimeToLong(this.time) > Tools.convertTimeToLong(other.time))
            return 1
        if(Tools.convertTimeToLong(this.time) < Tools.convertTimeToLong(other.time))
            return -1
        return 0
    }
}
