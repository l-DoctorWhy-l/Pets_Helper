package ru.rodipit.petshelper.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.UserEntity

class MainRepository(private val userDao: UserDao, private val animalDao: AnimalDao) {


    suspend fun checkUser(id: Int = 1) = withContext(Dispatchers.IO){
            val user = userDao.getUser(id)
            return@withContext user != null

    }

    suspend fun loadUser(id: Int = 1) = withContext(Dispatchers.IO){
        var user: UserEntity?
        coroutineScope {
            val userTask = async { userDao.getUser(id) }
            val animalTask = async { animalDao.getAnimals(id) }
            user = userTask.await()
            val animals =  animalTask.await().toMutableList()
            user?.animals = animals
        }
        return@withContext user
    }


    suspend fun loadUsersAnimals(userId: Int) : MutableList<AnimalEntity>{
        val animals = mutableListOf<AnimalEntity>()

        coroutineScope{
            launch(Dispatchers.IO) {
                val animalsTask = async { animalDao.getAnimals(userId) }
                animals.addAll(animalsTask.await())
            }

        }


        return animals
    }

    suspend fun addUser(userEntity: UserEntity) = withContext(Dispatchers.IO){
        userDao.insert(userEntity)
    }

    suspend fun addAnimal(animalEntity: AnimalEntity){
        animalDao.insert(animalEntity)
    }

    suspend fun loadAnimal(animalId: Int): AnimalEntity{
        return animalDao.getAnimal(animalId)
    }

}