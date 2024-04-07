package ru.rodipit.petshelper.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.Task
import ru.rodipit.petshelper.repository.MainRepository
import ru.rodipit.petshelper.repository.TaskRepository
import ru.rodipit.petshelper.ui.ui_states.MainScreenUiState
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject
    constructor(
    private val mainRepository: MainRepository,
    private val taskRepository: TaskRepository
    ) :ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState get() = _uiState.asStateFlow()


    fun loadAnimal(animalId: Int){
        if (animalId == -1)
            return
        println("Start animal")



        viewModelScope.launch(Dispatchers.IO) {
            val loadedAnimal = mainRepository.loadAnimal(animalId)

            loadCurrentTasks(animalId)

            withContext(Dispatchers.Main){
                _uiState.update { it.copy(currentAnimal = loadedAnimal) }
            }
        }
    }

    private fun loadCurrentTasks(animalId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val loadedTasks = taskRepository.loadCurrentDateTasks(animalId)
            withContext(Dispatchers.Main){
                _uiState.update { it.copy(currentTasks = loadedTasks) }
            }
        }
    }


    fun addTask(){
        viewModelScope.launch {
            val newTask =  Task(
                null,
                1,
                "AAAAA",
                "",
                "12/12/2004",
                "",
                0,
                Task.DAILY,
                Task.EATING,
                false
            )
            taskRepository.addTask(newTask)
            _uiState.update {
                it.copy(currentTasks = taskRepository.loadCurrentDateTasks(_uiState.value.currentAnimal.id!!))
            }
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            taskRepository.updateTask(task)
            var index = 0
            _uiState.value.currentTasks.forEachIndexed() { ind, item ->
                if(item.id == task.id){
                    index = ind
                    return@forEachIndexed
                }
            }

            _uiState.update {
                it.copy( currentTasks = it.currentTasks.toMutableList().apply { set(index, task) } )
            }
        }
    }

    fun showAddTaskDialog(){
        _uiState.update { it.copy(addTaskDialogShowing = true) }
    }

    fun closeAddTaskDialog(){
        _uiState.update { it.copy(addTaskDialogShowing = false) }
    }

}