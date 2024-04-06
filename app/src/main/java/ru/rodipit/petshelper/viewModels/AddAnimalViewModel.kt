package ru.rodipit.petshelper.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.Validator
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.repository.MainRepository
import ru.rodipit.petshelper.ui.ui_states.AddAnimalUiState
import javax.inject.Inject

@HiltViewModel
class AddAnimalViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(AddAnimalUiState())

    val uiState get() = _uiState.asStateFlow()

    fun setUserId(userId: Int){
        _uiState.update {
            it.copy(userId = userId)
        }
    }


    fun changeName(name: String){
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun changeAnimalType(animalType: Int){
        _uiState.update {
            it.copy(animalType = animalType)
        }
    }

    fun changeFullname(fullName: String){
        _uiState.update {
            it.copy(fullName = fullName)
        }
    }

    fun changeBirthDate(birthDate: String){
        _uiState.update {
            it.copy(bDay = birthDate)
        }
    }

    fun changeBreed(breed: String){
        _uiState.update {
            it.copy(breed = breed)
        }
    }

    fun createNewAnimal() {
        println("start create")
        if (Validator.validateName(requireNotNull(_uiState.value.name)) &&
            Validator.validateName(requireNotNull(_uiState.value.fullName)) &&
            Validator.validateBreed(requireNotNull(_uiState.value.breed)) &&
            _uiState.value.bDay != ""
        ){
            println("OKAY")
            val newAnimal =
                AnimalEntity(
                    null, _uiState.value.fullName, _uiState.value.name,
                    _uiState.value.bDay,
                    _uiState.value.breed,
                    1, _uiState.value.animalType)

            viewModelScope.launch {
                val job = launch(Dispatchers.IO) { mainRepository.addAnimal(newAnimal) }
                job.join()
                _uiState.update {
                    it.copy(isSuccess = true)
                }
            }
        }
    }

}