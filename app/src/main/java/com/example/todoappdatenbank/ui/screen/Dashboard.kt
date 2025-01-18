package com.example.todoappdatenbank.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Dashboard() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "active_todos") {
        composable("active_todos") {
            val context = LocalContext.current
            TodoScreen(
                context = context,
                navController = navController
            )
        }
        composable("completed_todos") {
            val context = LocalContext.current
            TodoScreen(
                context = context,
                navController = navController
            )
        }
    }
}