package ru.rodipit.petshelper.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.LoadingStates
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.db.AnimalsDb
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.UserEntity
import ru.rodipit.petshelper.models.MainUiState
import ru.rodipit.petshelper.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState get() = _uiState.asStateFlow()

    var loadingStates: MutableLiveData<MutableMap<String, LoadingStates>> = MutableLiveData(
        mutableMapOf(
        Pair("user", LoadingStates.START),
        Pair("animals", LoadingStates.START))
    )
    init {
        startLoading()
    }





    fun startLoading() = viewModelScope.launch {
        loadingStates.value = loadingStates.value?.apply {set("user", LoadingStates.LOADING)}
        val userDef = async{
            mainRepository.loadUser()
        }
        val user = userDef.await()
        _uiState.value.currentUser = user
        if(user?.animals != null) {
            _uiState.value.animals.clear()
            user.animals.forEach{ animal ->
                _uiState.value.animals.add(animal)
            }
            _uiState.value.animals.add(AnimalEntity())
        }
        loadingStates.value = loadingStates.value?.apply {set("user", LoadingStates.LOADED)}
        loadingStates.value = loadingStates.value?.apply {set("user", LoadingStates.SHOWING)}
    }

    fun nextAnimal(){
        if(_uiState.value.currentAnimalPosition < _uiState.value.animals.size - 1)
            _uiState.value.currentAnimalPosition++
    }

    fun previousAnimal() {
        if(_uiState.value.currentAnimalPosition > 0)
            _uiState.value.currentAnimalPosition--
    }

    fun startLoadingAnimals(){
        loadingStates.value = loadingStates.value?.apply {set("animals", LoadingStates.START)}
    }

}