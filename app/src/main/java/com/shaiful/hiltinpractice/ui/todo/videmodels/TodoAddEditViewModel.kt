package com.shaiful.hiltinpractice.ui.todo.videmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaiful.hiltinpractice.data.entities.Todo
import com.shaiful.hiltinpractice.domain.repositories.TodoRepository
import com.shaiful.hiltinpractice.events.TodoAddEditEvent
import com.shaiful.hiltinpractice.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoAddEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                todoRepository.getTodoById(todoId)?.let { todoFromDb ->
                    title = todoFromDb.title
                    description = todoFromDb.description ?: ""
                    this@TodoAddEditViewModel.todo = todoFromDb
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun onEvent(event: TodoAddEditEvent) {
        when (event) {
            is TodoAddEditEvent.OnDescriptionChange -> {
                description = event.description
            }

            is TodoAddEditEvent.OnTitleChange -> {
                title = event.title
            }

            is TodoAddEditEvent.OnTodoSave -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                            message = "Title must not be empty."
                        ))
                        return@launch
                    }

                    todoRepository.insertTodo(
                        Todo(
                            id = todo?.id,
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false
                        )
                    )

                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }
}