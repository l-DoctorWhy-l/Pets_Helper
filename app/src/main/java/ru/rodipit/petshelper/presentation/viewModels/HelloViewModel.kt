package ru.rodipit.petshelper.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.UserEntity
import ru.rodipit.petshelper.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class HelloViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {


    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    val userName get() = _userName.asStateFlow()
    private val _regIsComplete: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val regIsComplete get() = _regIsComplete.asStateFlow()






    fun updateUserName(userName: String){
        this._userName.value = userName
    }


    fun createUser(userName: String){
        viewModelScope.launch{

            val newUser = UserEntity(userName.trim())
            mainRepository.addUser(newUser)

            _regIsComplete.value = true

        }

    }

}