package com.shaiful.hiltinpractice.ui.todo.widgets

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shaiful.hiltinpractice.ui.todo.videmodels.TodoViewModel
import com.shaiful.hiltinpractice.events.TodoEvent
import com.shaiful.hiltinpractice.events.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    todoViewModel: TodoViewModel = hiltViewModel()
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val todoList = todoViewModel.todoList.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        todoViewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it)
                }

                is UiEvent.ShowSnackBar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        todoViewModel.onEvent(TodoEvent.OnDeleteUndo)
                    }
                }

                else -> {
                    Unit
                }
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Todo App",
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    todoViewModel.onEvent(TodoEvent.OnTodoAdd)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(.6.dp)
                        .background(
                            color = Color.Gray
                        )
                )
                LazyColumn {
                    items(todoList.value) { todo ->
                        TodoItemWidget(
                            todo = todo,
                            onEvent = todoViewModel::onEvent,
                            modifier = Modifier.clickable {
                                todoViewModel.onEvent(TodoEvent.OnTodoTap(todo))
                            }
                        )
                    }
                }
            }
        }
    )
}