package com.shaiful.hiltinpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shaiful.hiltinpractice.ui.navigation.RouteNames
import com.shaiful.hiltinpractice.ui.todo.widgets.TodoAddEditScreen
import com.shaiful.hiltinpractice.ui.todo.widgets.TodoListScreen
import com.shaiful.hiltinpractice.ui.theme.HiltInPracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltInPracticeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = RouteNames.todoListScreen
                ){
                    composable(RouteNames.todoListScreen){
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = RouteNames.addEditTodoScreen + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        TodoAddEditScreen(onPopBackStack = {navController.popBackStack()})
                    }
                }
            }
        }
    }
}