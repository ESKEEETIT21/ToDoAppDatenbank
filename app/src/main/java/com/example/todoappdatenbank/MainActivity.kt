package com.example.todoappdatenbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.todoappdatenbank.ui.screen.Dashboard

/**
 * MainActivity class for initializing the app and setting the content to the Dashboard composable.
 *
 * This activity is responsible for launching the app's main UI with the Dashboard screen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dashboard()
        }
    }
}