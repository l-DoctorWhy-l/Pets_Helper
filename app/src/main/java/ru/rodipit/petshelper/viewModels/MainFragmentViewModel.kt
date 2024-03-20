package ru.rodipit.petshelper.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _currentTasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val currentTasks: StateFlow<MutableList<Task>>
        get() = _currentTasks.asStateFlow()

    private val _animal = MutableStateFlow(AnimalEntity())
    val animal: StateFlow<AnimalEntity>
        get() = _animal.asStateFlow()


    fun loadAnimal(animalId: Int){
        if (animalId == -1)
            return
        println("Start animal")



        viewModelScope.launch(Dispatchers.IO) {
            val loadedAnimal = repository.loadAnimal(animalId)

            loadCurrentTasks(animalId)

            withContext(Dispatchers.Main){
                _animal.value = loadedAnimal
            }
        }
    }

    private fun loadCurrentTasks(animalId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val loadedTasks = taskRepository.loadCurrentDateTasks(animalId)
            withContext(Dispatchers.Main){
                _currentTasks.value = loadedTasks
                println(currentTasks.value)
            }
        }
    }

    fun updateTasks(){

    }

    fun addTask(){
        viewModelScope.launch {
            val newTask =  Task(
                null,
                1,
                "AAAAA",
                "BBBB",
                System.currentTimeMillis() + 10000,
                0,
                Task.DAILY,
                Task.EATING,
                false
            )
            taskRepository.addTask(newTask)

            _currentTasks.value = taskRepository.loadCurrentDateTasks(animal.value.id!!)
        }
    }


}