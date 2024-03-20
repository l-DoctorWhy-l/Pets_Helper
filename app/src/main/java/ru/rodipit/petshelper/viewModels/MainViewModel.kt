package ru.rodipit.petshelper.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.LoadingStates
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.db.AnimalsDb
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.UserEntity
import ru.rodipit.petshelper.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val currentUser: MutableLiveData<UserEntity?> = MutableLiveData(null)
    val currentAnimalPosition: MutableLiveData<Int> = MutableLiveData(0)
    val animals: MutableLiveData<MutableList<AnimalEntity>> = MutableLiveData(mutableListOf(AnimalEntity()))
    var currentAnimal = animals.value?.get(0)
    private var animalsLoadingJob: Job? = null
    var state: MutableLiveData<String> = MutableLiveData("loading")
    var loadingStates: MutableLiveData<MutableMap<String, LoadingStates>> = MutableLiveData(
        mutableMapOf(
        Pair("user", LoadingStates.START),
        Pair("animals", LoadingStates.START))
    )


    init {

        createObservers()

        startLoading()

    }





    fun startLoading() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main){ loadingStates.value = loadingStates.value?.apply {set("user", LoadingStates.LOADING)} }
        val user = mainRepository.loadUser(1)
        withContext(Dispatchers.Main) {
            if (user == null)
                state.value = "registration"
            else{
                state.value = "using"
                currentUser.value = user
                loadingStates.value = loadingStates.value?.apply {set("user", LoadingStates.LOADED)}
                loadingStates.value = loadingStates.value?.apply {set("user", LoadingStates.SHOWING)}

            }
        }
    }


    private fun loadAnimals(userId: Int, currentPet: Int = 0) = viewModelScope.launch(Dispatchers.IO) {

        withContext(Dispatchers.Main){
            loadingStates.value = loadingStates.value?.apply {set("animals", LoadingStates.LOADING)}
        }

        val loadedAnimals = mainRepository.loadUsersAnimals(userId)




        withContext(Dispatchers.Main){
            if (animals.value != null){
                animals.value = loadedAnimals.apply { add(AnimalEntity()) }
            }
            loadingStates.value = loadingStates.value?.apply {set("animals", LoadingStates.LOADED)}
            currentAnimalPosition.value = currentPet
        }
    }


    private fun createObservers(){

        loadingStates.observeForever {

            if ((it["user"] == LoadingStates.LOADED || it["user"] == LoadingStates.SHOWING)
                && currentUser.value != null
                && (it["animals"] == LoadingStates.START || it["animals"] == LoadingStates.FAILED)){
                if (animalsLoadingJob?.isActive != true) /*job == null or jon isnt active*/{
                    println("LOADING ANIMALS STARTED")
                    animalsLoadingJob = loadAnimals(requireNotNull(currentUser.value).id)
                }
            }
        }

    currentAnimalPosition.observeForever {
        currentAnimal = animals.value?.get(it)
    }


    }



    fun nextAnimal(){
        if(requireNotNull(currentAnimalPosition.value) < requireNotNull(animals.value).size - 1)
            currentAnimalPosition.value = currentAnimalPosition.value?.plus(1)
    }

    fun previousAnimal() {
        if(requireNotNull(currentAnimalPosition.value) > 0)
            currentAnimalPosition.value = currentAnimalPosition.value?.minus(1)
    }

    fun startLoadingAnimals(){
        loadingStates.value = loadingStates.value?.apply {set("animals", LoadingStates.START)}
    }

}