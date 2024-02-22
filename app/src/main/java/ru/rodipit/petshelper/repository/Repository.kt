package ru.rodipit.petshelper.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.UserEntity

class Repository(private val userDao: UserDao, private val animalDao: AnimalDao) {

    suspend fun loadUser(id: Int): UserEntity?{
        var user: UserEntity?
        coroutineScope {
            val userTask = async { userDao.getUser(id) }
            val animalTask = async { animalDao.getAnimals(id) }
            user = userTask.await()
            val animals =  animalTask.await().toMutableList()
            user?.animals = animals
        }
        println(user)
        return user
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

    suspend fun addAnimal(animalEntity: AnimalEntity){
        animalDao.insert(animalEntity)
    }

    suspend fun loadAnimal(animalId: Int): AnimalEntity{
        return animalDao.getAnimal(animalId)
    }

}