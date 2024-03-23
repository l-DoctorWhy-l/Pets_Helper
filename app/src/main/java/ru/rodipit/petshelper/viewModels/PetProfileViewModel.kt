package ru.rodipit.petshelper.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.rodipit.petshelper.repository.MainRepository
import javax.inject.Inject
@HiltViewModel
class PetProfileViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

}