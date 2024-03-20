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
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.UserEntity
import ru.rodipit.petshelper.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class HelloViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {


    val userName: MutableLiveData<String> = MutableLiveData("")
    val regIsComplete: MutableLiveData<Boolean> = MutableLiveData(false)






    fun updateUserName(userName: String){
        this.userName.value = userName
    }


    fun createUser(userName: String){
        viewModelScope.launch{

            val newUser = UserEntity(userName.trim())
            mainRepository.addUser(newUser)

            regIsComplete.value = true

        }

    }

}