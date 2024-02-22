package ru.rodipit.petshelper.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.TaskDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.db.AnimalsDb
import ru.rodipit.petshelper.data.db.TasksDb
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.Task
import ru.rodipit.petshelper.repository.Repository
import ru.rodipit.petshelper.repository.TaskRepository

class MainFragmentViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: UserDao = UsersDb.getInstance(application.applicationContext).getDao()
    private var animalDao: AnimalDao = AnimalsDb.getInstance(application.applicationContext).getDao()
    private var taskDao: TaskDao = TasksDb.getInstance(application.applicationContext).getDao()
    private val repository: Repository = Repository(userDao, animalDao)
    private val taskRepository: TaskRepository = TaskRepository(taskDao)

    val currentTasks = MutableLiveData<MutableList<Task>>(mutableListOf())

    val animal = MutableLiveData(AnimalEntity())


    fun loadAnimal(animalId: Int){
        if (animalId == -1)
            return
        println("Start animal")



        viewModelScope.launch(Dispatchers.IO) {
            val loadedAnimal = repository.loadAnimal(animalId)

            loadCurrentTasks()

            withContext(Dispatchers.Main){
                animal.value = loadedAnimal
            }
        }
    }

    private fun loadCurrentTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            val loadedTasks = taskRepository.loadCurrentDateTasks(requireNotNull(animal.value?.id))
            withContext(Dispatchers.Main){
                currentTasks.value = loadedTasks
            }
        }
    }


}