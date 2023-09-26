package com.shaiful.hiltinpractice.ui.todo.widgets

import androidx.compose.foundation.clickable
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
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
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
                    .padding(16.dp)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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