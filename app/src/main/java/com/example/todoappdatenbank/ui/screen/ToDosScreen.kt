@file:OptIn(ExperimentalFoundationApi::class)

package com.example.todoappdatenbank.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoappdatenbank.database.controller.ToDoController
import com.example.todoappdatenbank.database.dataclass.ToDoDataClass
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
@Composable
fun TodoScreen(
    context: Context,
    navController: NavHostController = rememberNavController(),
    showCompletedOnly: Boolean
) {
    val todoController = ToDoController(context)

    var todos by remember {
        mutableStateOf(
            todoController.getAllTodos().filter { it.state == showCompletedOnly }
        )
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var selectedTodo by remember { mutableStateOf<ToDoDataClass?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.navigate("dashboard") {
                    popUpTo("dashboard") { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Zurück zum Dashboard"
                )
            }

            Text(
                text = if (showCompletedOnly) "Completed Todos" else "Active Todos",
                style = MaterialTheme.typography.titleLarge
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                ExpandableToDoCard(
                    todo = todo,
                    onEditClick = {
                        selectedTodo = todo
                        showEditDialog = true
                    },
                    onStateChange = { isChecked ->
                        val updatedTodo = todo.copy(state = isChecked)
                        todoController.updateTodo(updatedTodo)

                        todos = todoController.getAllTodos().filter { it.state == showCompletedOnly }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!showCompletedOnly) {
            Button(
                onClick = {
                    selectedTodo = null
                    showEditDialog = true
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        }

        if (showEditDialog) {
            EditToDoDialog(
                todo = selectedTodo,
                onDismiss = { showEditDialog = false },
                onSave = { todo ->
                    if (todo.id == 0) {
                        todoController.insertTodo(todo)
                    } else {
                        todoController.updateTodo(todo)
                    }

                    todos = todoController.getAllTodos().filter { it.state == showCompletedOnly }
                    showEditDialog = false
                },
                onDelete = { todo ->
                    todoController.deleteTodo(todo.id)

                    todos = todoController.getAllTodos().filter { it.state == showCompletedOnly }
                    showEditDialog = false
                }
            )
        }
    }
}


@Composable
fun ExpandableToDoCard(
    todo: ToDoDataClass,
    onEditClick: () -> Unit,
    onStateChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val todoController = ToDoController(LocalContext.current)
    val priorities = todoController.getAllPriorities()

    val fallbackDeadline = LocalDateTime.now().plusMonths(1)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'um' HH:mm")
    val formattedDeadline = todo.deadline.format(formatter) ?: fallbackDeadline.format(formatter)

    val priorityLevel = priorities.firstOrNull { it.id == todo.priority }?.level ?: "Unbekannt"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!expanded) {
                    expanded = true
                }
            }
            .combinedClickable(
                onClick = { expanded = !expanded },
                onLongClick = {
                    if (expanded) {
                        onEditClick()
                    }
                }
            ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = todo.state,
                        onCheckedChange = { isChecked -> onStateChange(isChecked) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = todo.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Einklappen" else "Ausklappen"
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        todo.description,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Priority: ${priorityLevel}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${formattedDeadline} Uhr",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Lang drücken zum Bearbeiten",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}


@Composable
fun EditToDoDialog (
    todo: ToDoDataClass?,
    onDismiss: () -> Unit,
    onSave: (ToDoDataClass) -> Unit,
    onDelete: (ToDoDataClass) -> Unit
) {
    val todoController = ToDoController(LocalContext.current)
    val priorities = remember { todoController.getAllPriorities() }

    var name by remember { mutableStateOf(todo?.name ?: "") }
    var description by remember { mutableStateOf(todo?.description ?: "") }
    var selectedPriorityId by remember { mutableIntStateOf(todo?.priority ?: priorities.firstOrNull()?.id ?: 1) }
    var deadline by remember {
        mutableStateOf(
            todo?.deadline?.toString()
                ?: LocalDateTime.now().plusMonths(1).toString()
        )
    }
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    fun openTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            val dateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())
            deadline = dateTime.toString()
        }

        TimePickerDialog(
            context,
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    fun openDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            openTimePicker()
        }

        DatePickerDialog(
            context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (todo == null) "Neues Todo anlegen" else "Todo bearbeiten")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Box {
                    Text(
                        text = priorities.firstOrNull { it.id == selectedPriorityId }?.level ?: "Select Priority",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .padding(8.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        priorities.forEach { priority ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = { Text(priority.level) },
                                onClick = {
                                    selectedPriorityId = priority.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                val formattedDeadline = remember(deadline) {
                    LocalDateTime.parse(deadline).format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm"))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { openDatePicker() }
                        .padding(8.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                ) {
                    Text(text = formattedDeadline)
                }

            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.trim().isEmpty()) {
                    Toast.makeText(context, "Name darf nicht leer sein!", Toast.LENGTH_SHORT, ).show()
                } else {
                    val updatedTodo = ToDoDataClass(
                        id = todo?.id ?: 0,
                        name = name,
                        description = description,
                        priority = selectedPriorityId,
                        deadline = LocalDateTime.parse(deadline),
                        state = false
                    )
                    onSave(updatedTodo)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            if (todo != null) {
                Button(onClick = { onDelete(todo) }) {
                    Text("Remove")
                }
            }
        }
    )
}