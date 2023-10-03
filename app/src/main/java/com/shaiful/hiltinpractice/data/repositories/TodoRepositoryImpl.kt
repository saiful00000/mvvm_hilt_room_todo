package com.shaiful.hiltinpractice.data.repositories

import com.shaiful.hiltinpractice.data.dao.TodoDao
import com.shaiful.hiltinpractice.data.entities.Todo
import com.shaiful.hiltinpractice.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return todoDao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return todoDao.getTodos()
    }

}