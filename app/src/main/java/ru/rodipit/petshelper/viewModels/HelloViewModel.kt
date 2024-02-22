package ru.rodipit.petshelper.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.UserEntity

class HelloViewModel(application: Application): AndroidViewModel(application) {


    private var userDao = UsersDb.getInstance(application.applicationContext).getDao()
    val userName: MutableLiveData<String> = MutableLiveData("")
    val regIsComplete: MutableLiveData<Boolean> = MutableLiveData(false)






    fun updateUserName(userName: String){
        this.userName.value = userName
    }


    fun createUser(userName: String){
        viewModelScope.launch(Dispatchers.IO) {

            val newUser = UserEntity(userName.trim())
            userDao.insert(newUser)

            withContext(Dispatchers.Main.immediate){
                regIsComplete.value = true
            }

        }

    }

}