package com.shaiful.hiltinpractice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaiful.hiltinpractice.data.entities.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("select * from todo where id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Query("select * from todo")
    fun getTodos(): Flow<List<Todo>>

}