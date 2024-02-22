package ru.rodipit.petshelper.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(var name: String, @PrimaryKey(autoGenerate = false) val id: Int = 1) {

    @Ignore
    var animals : MutableList<AnimalEntity> = mutableListOf()

    override fun toString(): String {
        return "UserEntity(name='$name', id=$id, animals=$animals)"
    }

}