package com.shaiful.hiltinpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shaiful.hiltinpractice.data.dao.TodoDao
import com.shaiful.hiltinpractice.data.entities.Todo

@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao
}