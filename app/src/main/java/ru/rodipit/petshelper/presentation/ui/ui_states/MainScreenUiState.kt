package ru.rodipit.petshelper.presentation.ui.ui_states

import android.app.AlertDialog
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.Task

data class MainScreenUiState(
    var currentTasks: MutableList<Task> = arrayListOf(),
    var currentAnimal: AnimalEntity = AnimalEntity(),
    var addTaskDialogShowing: Boolean = false,
)
