package com.example.todoappdatenbank.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Dashboard() {
    val navController = rememberNavController()
    var showCompletedOnly by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        showCompletedOnly = false // Zeige aktive Todos
                        navController.navigate("active_todos")
                    }
                ) {
                    Text("Active Todos")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showCompletedOnly = true // Zeige abgeschlossene Todos
                        navController.navigate("completed_todos")
                    }
                ) {
                    Text("Completed Todos")
                }
            }
        }

        composable("active_todos") {
            TodoScreen(
                context = LocalContext.current,
                showCompletedOnly = false,
                navController = navController
            )
        }
        composable("completed_todos") {
            TodoScreen(
                context = LocalContext.current,
                showCompletedOnly = true,
                navController = navController
            )
        }

    }
}