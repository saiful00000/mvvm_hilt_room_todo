package com.shaiful.hiltinpractice.events

import com.shaiful.hiltinpractice.data.entities.Todo

sealed class TodoEvent {
    data class DeleteTodo(val todo: Todo): TodoEvent()
    data class OnDone(val todo: Todo, val onDone: Boolean): TodoEvent()
    object OnDeleteUndo: TodoEvent()
    data class OnTodoTap(val todo: Todo): TodoEvent()
    object OnTodoAdd: TodoEvent()

}