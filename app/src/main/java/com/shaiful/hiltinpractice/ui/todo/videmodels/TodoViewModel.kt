package com.shaiful.hiltinpractice.ui.todo.videmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaiful.hiltinpractice.data.entities.Todo
import com.shaiful.hiltinpractice.domain.repositories.TodoRepository
import com.shaiful.hiltinpractice.ui.navigation.RouteNames
import com.shaiful.hiltinpractice.events.TodoEvent
import com.shaiful.hiltinpractice.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todoList = todoRepository.getTodos()
    private var deletedTodoCache: Todo? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun onEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.DeleteTodo -> {
                viewModelScope.launch {
                    deletedTodoCache = event.todo
                    todoRepository.deleteTodo(event.todo)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = "Todo deleted.",
                            action = "Undo"
                        )
                    )
                }
            }

            is TodoEvent.OnDone -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        event.todo.copy(
                            isDone = event.onDone
                        )
                    )
                }
            }

            is TodoEvent.OnDeleteUndo -> {
                deletedTodoCache?.let { todo ->
                    viewModelScope.launch {
                        todoRepository.insertTodo(todo)
                    }
                }
            }

            is TodoEvent.OnTodoAdd -> {
                sendUiEvent(UiEvent.Navigate(RouteNames.addEditTodoScreen))
            }

            is TodoEvent.OnTodoTap -> {
                sendUiEvent(UiEvent.Navigate(RouteNames.addEditTodoScreen + "?todoId=${event.todo.id}"))
            }
        }
    }

}