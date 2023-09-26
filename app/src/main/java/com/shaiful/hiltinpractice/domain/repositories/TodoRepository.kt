package com.shaiful.hiltinpractice.domain.repositories

import com.shaiful.hiltinpractice.data.entities.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int): Todo?

    fun getTodos(): Flow<List<Todo>>
}