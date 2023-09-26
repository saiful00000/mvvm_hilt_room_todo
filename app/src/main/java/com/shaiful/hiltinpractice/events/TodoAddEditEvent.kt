package com.shaiful.hiltinpractice.events

sealed class TodoAddEditEvent{
    data class OnTitleChange(val title: String): TodoAddEditEvent()
    data class OnDescriptionChange(val description: String): TodoAddEditEvent()
    object OnTodoSave: TodoAddEditEvent()
}
