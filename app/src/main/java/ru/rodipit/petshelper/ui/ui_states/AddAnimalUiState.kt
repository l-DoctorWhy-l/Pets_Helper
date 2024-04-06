package ru.rodipit.petshelper.ui.ui_states

import ru.rodipit.petshelper.data.entities.AnimalEntity

data class AddAnimalUiState(
    var userId: Int = -1,
    var name: String = "",
    var fullName: String = "",
    var bDay: String = "",
    var breed: String = "",
    var animalType: Int = AnimalEntity.OTHER,
    var isSuccess: Boolean = false
)