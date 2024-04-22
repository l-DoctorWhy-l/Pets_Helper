package ru.rodipit.petshelper.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.entities.Task
import ru.rodipit.petshelper.data.repository.MainRepository
import ru.rodipit.petshelper.data.repository.TaskRepository
import ru.rodipit.petshelper.presentation.ui.ui_states.MainScreenUiState
import javax.inject.Inject

@HiltViewModel
class EatingScreenViewModel  @Inject
    constructor(
        private val mainRepository: MainRepository,
        private val taskRepository: TaskRepository
): ViewModel() {

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
            Log.d("TAG", loadedAnimal.toString())
            Log.d("TAG", uiState.value.currentTasks.toString())
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


    fun addTask(task: Task){
        viewModelScope.launch {
            taskRepository.addTask(task)
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
}
