package com.shaiful.hiltinpractice.ui.todo.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shaiful.hiltinpractice.data.entities.Todo
import com.shaiful.hiltinpractice.events.TodoEvent

@Composable
fun TodoItemWidget(
    todo: Todo,
    onEvent: (TodoEvent) -> Unit,
    modifier: Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.padding(8.dp, 4.dp)
    ) {
        Row(
            modifier = modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = todo.isDone, onCheckedChange = { checked ->
                onEvent(TodoEvent.OnDone(todo, checked))
            })
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(.8f)
            ) {
                Text(text = todo.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = todo.description ?: "-", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    onEvent(TodoEvent.DeleteTodo(todo))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }
}