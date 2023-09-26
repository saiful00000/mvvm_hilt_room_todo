package com.shaiful.hiltinpractice.ui.todo.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shaiful.hiltinpractice.ui.todo.videmodels.TodoAddEditViewModel
import com.shaiful.hiltinpractice.events.TodoAddEditEvent
import com.shaiful.hiltinpractice.events.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAddEditScreen(
    onPopBackStack: () -> Unit,
    todoAddEditViewModel: TodoAddEditViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        todoAddEditViewModel.uiEvent.collect {
            when (it) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UiEvent.ShowSnackBar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
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
                        text = "Add Todo",
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
                    todoAddEditViewModel.onEvent(TodoAddEditEvent.OnTodoSave)
                },
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Create")
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
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = todoAddEditViewModel.title,
                    onValueChange = {
                        todoAddEditViewModel.onEvent(TodoAddEditEvent.OnTitleChange(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = todoAddEditViewModel.description,
                    singleLine = false,
                    maxLines = 5,
                    onValueChange = {
                        todoAddEditViewModel.onEvent(TodoAddEditEvent.OnDescriptionChange(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )
            }
        }
    )

}