package ru.rodipit.petshelper.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.Validator
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.db.AnimalsDb
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.models.Animal
import ru.rodipit.petshelper.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class AddAnimalViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val name = MutableLiveData("")
    val fullname = MutableLiveData("")
    val birthDate = MutableLiveData("")
    val breed = MutableLiveData("")
    private val animalType = MutableLiveData(Animal.OTHER)
    val success = MutableLiveData(false)







    fun changeName(name: String){
        this.name.value = name
    }

    fun changeAnimalType(animalType: Int){
        this.animalType.value = animalType
    }

    fun changeFullname(fullname: String){
        this.fullname.value = fullname
    }

    fun changeBirthDate(birthDate: String){
        this.birthDate.value = birthDate
    }

    fun changeBreed(breed: String){
        this.breed.value = breed
    }

    fun createNewAnimal() {
        if (Validator.validateName(requireNotNull(name.value)) &&
            Validator.validateName(requireNotNull(fullname.value)) &&
            Validator.validateBreed(requireNotNull(breed.value)) &&
            Validator.validateDate(requireNotNull(birthDate.value)) != 0L
        ){

            val newAnimal =
                AnimalEntity(
                    null, fullname.value!!, name.value!!,
                    Validator.validateDate(requireNotNull(birthDate.value)),
                    breed.value!!, 1, animalType.value!!)

            viewModelScope.launch(Dispatchers.IO) {
                val addAnimalJob = mainRepository.addAnimal(newAnimal)
                withContext(Dispatchers.Main){
                    success.value = true
                }
            }
        }
    }

}