package ru.rodipit.petshelper.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.rodipit.petshelper.data.dao.AnimalDao
import ru.rodipit.petshelper.data.dao.TaskDao
import ru.rodipit.petshelper.data.dao.UserDao
import ru.rodipit.petshelper.data.db.AnimalsDb
import ru.rodipit.petshelper.data.db.TasksDb
import ru.rodipit.petshelper.data.db.UsersDb
import ru.rodipit.petshelper.data.repository.MainRepository
import ru.rodipit.petshelper.data.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideUserDao(@ApplicationContext context: Context): UserDao{
        return UsersDb.getInstance(context).getDao()
    }
    @Provides
    fun provideAnimalDao(@ApplicationContext context: Context): AnimalDao{
        return AnimalsDb.getInstance(context).getDao()
    }
    @Provides
    fun provideTaskDao(@ApplicationContext context: Context): TaskDao{
        return TasksDb.getInstance(context).getDao()
    }
    @Provides
    fun provideMainRepository(userDao: UserDao, animalDao: AnimalDao): MainRepository {
        return MainRepository(userDao, animalDao)
    }
    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }

}