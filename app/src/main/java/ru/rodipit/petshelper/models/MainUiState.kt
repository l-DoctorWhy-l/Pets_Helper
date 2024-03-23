package ru.rodipit.petshelper.models

import kotlinx.coroutines.flow.MutableStateFlow
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.UserEntity

data class MainUiState(
    var currentUser: UserEntity? = null,
    var currentAnimalPosition: Int = 0,
    var animals: MutableList<AnimalEntity> = mutableListOf(AnimalEntity()),





)
