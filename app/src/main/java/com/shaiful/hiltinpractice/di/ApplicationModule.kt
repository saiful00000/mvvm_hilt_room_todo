package com.shaiful.hiltinpractice.di

import android.app.Application
import androidx.room.Room
import com.shaiful.hiltinpractice.data.local.TodoDatabase
import com.shaiful.hiltinpractice.data.repositories.TodoRepositoryImpl
import com.shaiful.hiltinpractice.domain.repositories.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application) : TodoDatabase {
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            "todo_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoDatabase: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(todoDatabase.todoDao);
    }

}