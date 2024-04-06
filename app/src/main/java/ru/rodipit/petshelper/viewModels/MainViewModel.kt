package ru.rodipit.petshelper.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.ui.ui_states.MainUiState
import ru.rodipit.petshelper.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState get() = _uiState.asStateFlow()


    init {
        startLoading()
    }





    private fun startLoading() = viewModelScope.launch {
        val userDef = async{
            mainRepository.loadUser()
        }
        val user = userDef.await()
        _uiState.update { it.copy(currentUser = user) }
        if(user?.animals != null) {
            _uiState.update { it.copy(animals = it.animals.apply { clear() }) }
            user.animals.forEach{ animal ->
                _uiState.update { it.copy(animals = it.animals.apply { add(animal) }) }
            }
            _uiState.update { it.copy(animals = it.animals.apply { add(AnimalEntity()) }) }
        }

    }

    fun nextAnimal(){
        if(_uiState.value.currentAnimalPosition < _uiState.value.animals.size - 1){
            _uiState.update {
                it.copy(currentAnimalPosition = _uiState.value.currentAnimalPosition + 1)
            }
        }
    }

    fun previousAnimal() {
        if(_uiState.value.currentAnimalPosition > 0){
            _uiState.update {
                it.copy(currentAnimalPosition = _uiState.value.currentAnimalPosition - 1)
            }
        }
    }


}