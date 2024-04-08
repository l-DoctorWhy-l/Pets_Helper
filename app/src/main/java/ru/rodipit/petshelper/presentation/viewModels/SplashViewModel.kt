package ru.rodipit.petshelper.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.core.LoadingStates
import ru.rodipit.petshelper.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    private val _isRegistered = MutableStateFlow(LoadingStates.LOADING)
    val isRegistered get() = _isRegistered.asStateFlow()

    fun checkCurrentUser() {
        println("checking")
        viewModelScope.launch {
            val result = mainRepository.checkUser()
            println(result)
            if(result)
                _isRegistered.value = LoadingStates.START
            else
                _isRegistered.value = LoadingStates.FAILED
        }
    }

}