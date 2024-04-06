package ru.rodipit.petshelper.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.rodipit.petshelper.core.Tools
import java.util.Calendar

@Parcelize
@Entity(tableName = "Animals")
data class AnimalEntity(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                        var fullName: String,
                        var name: String,
                        var birthday: String,
                        var breed: String,
                        var userId: Int?,
                        var type: Int): Parcelable
{


    constructor() : this(-1, "", "", "", "", -1, -1)


    fun getAge(): Int{

        val now = Calendar.getInstance()

        val age = Calendar.getInstance().apply {
            timeInMillis = Tools.convertTimeToLong(birthday)
        }

        if(now.get(Calendar.DAY_OF_YEAR) < age.get(Calendar.DAY_OF_YEAR) )
            return now.get(Calendar.YEAR) - age.get(Calendar.YEAR) - 1

        return now.get(Calendar.YEAR) - age.get(Calendar.YEAR)
    }


    companion object{
        const val CAT = 1
        const val DOG = 2
        const val FISH = 3
        const val PARROT = 4
        const val TURTLE = 5
        const val HAMSTER = 6
        const val OTHER = 0


        fun castAnimalTypeFromStringToInt(animalType: String) = when(animalType) {
            "Cat" -> CAT
            "Dog" -> DOG
            "Fish" -> FISH
            "Parrot" -> PARROT
            "Turtle" -> TURTLE
            "Hamster" -> HAMSTER
            "Other" -> OTHER
            else -> OTHER
        }

        fun castAnimalTypeFromIntToString(animalType: Int) = when(animalType) {
            CAT -> "Cat"
            DOG -> "Dog"
            FISH -> "Fish"
            PARROT -> "Parrot"
            TURTLE -> "Turtle"
            HAMSTER -> "Hamster"
            OTHER -> "Other"
            else -> "Other"
        }

    }

}