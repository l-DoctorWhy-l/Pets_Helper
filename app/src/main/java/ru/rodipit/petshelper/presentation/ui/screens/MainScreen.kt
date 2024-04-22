package ru.rodipit.petshelper.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.core.Tools
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.data.entities.Task
import ru.rodipit.petshelper.presentation.ui.TaskItemWidget
import ru.rodipit.petshelper.presentation.ui.ui_states.MainScreenUiState
import ru.rodipit.petshelper.presentation.viewModels.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    animalId: Int,
    innerPadding: PaddingValues,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadAnimal(animalId)
    }


    Log.d("TAG", "MainScreen")
    if (animalId != -1) {
        AnimalScreen(uiState = uiState, viewModel, innerPadding = innerPadding)
    } else {
        NeedAnimalScreen(innerPadding = innerPadding)
    }

}

@Composable
fun AnimalScreen(
    uiState: MainScreenUiState,
    viewModel: MainScreenViewModel,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(5.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimalInfoTopBar()

        AnimalInfoComponent(uiState.currentAnimal)

        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(40.dp, 50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Today Tasks",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            IconButton(
                onClick = {
                    viewModel.showAddTaskDialog()
                },
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                    contentDescription = "add task",
                    Modifier.scale(1.5f)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.currentTasks, key = { task ->
                task.id!!
            }) { task ->
                TaskItemWidget.TaskItem(task = task, onChangeState = {
                    viewModel.updateTask(task.copy(state = it))
                })
            }
        }
        if (uiState.addTaskDialogShowing) {
            AddTaskDialog(
                modifier = Modifier,
                onDismiss = { viewModel.closeAddTaskDialog() },
                animalId = uiState.currentAnimal.id!!,
                onAddTask = viewModel::addTask
            )

        }
    }
}

@Composable
private fun AnimalInfoTopBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .heightIn(30.dp, 40.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Info",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = "edit",
                Modifier.scale(1.5f)
            )
        }

    }
}

@Composable
fun AnimalInfoComponent(
    currentAnimal: AnimalEntity
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Fullname: ${currentAnimal.fullName}",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Age: ${currentAnimal.getAge()}",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Birthday: ${currentAnimal.birthday}",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Breed: ${currentAnimal.breed}",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    animalId: Int,
    onAddTask: (task: Task) -> Unit
) {


    val coroutineScope = rememberCoroutineScope()

    BasicAlertDialog(onDismissRequest = onDismiss) {
        var title by remember {
            mutableStateOf("")
        }

        var description by remember {
            mutableStateOf("")
        }
        var date by remember {
            mutableStateOf("")
        }
        var time by remember {
            mutableStateOf("12:00")
        }

        var dateError by remember {
            mutableStateOf(false)
        }
        var timeError by remember {
            mutableStateOf(false)
        }



        Card(
            modifier = modifier,
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(
                width = 2.dp,
                color = Color.Cyan
            )
        ) {


            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Title",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Red)
                            .size(24.dp),
                        onClick = onDismiss,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_clear_24),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = title,
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true,
                    onValueChange = { title = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier,
                    value = description,
                    maxLines = 4,
                    onValueChange = { description = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Repeat mode",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                val repeatModes = mapOf(
                    "One time" to Task.ONE_TIME,
                    "Daily" to Task.DAILY,
                    "Weekly" to Task.WEEKLY,
                    "Monthly" to Task.MONTHLY,
                    "Half year" to Task.HALF_YEAR,
                    "Yearly" to Task.YEARLY
                )

                var selectedRepeatMode by remember {
                    mutableStateOf("One time")
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(repeatModes.keys.toList()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedRepeatMode = it
                                }
                                .background(
                                    if (selectedRepeatMode == it) Color.LightGray
                                    else Color.White
                                )
                                .padding(start = 15.dp, end = 15.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.scrim,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Task type",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                val taskTypes = mapOf(
                    "Health" to Task.HEALTH,
                    "Eating" to Task.EATING,
                    "Other" to Task.WEEKLY,
                )

                var selectedTaskType by remember {
                    mutableStateOf("Health")
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(taskTypes.keys.toList()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedTaskType = it
                                }
                                .background(
                                    if (selectedTaskType == it) Color.LightGray
                                    else Color.White
                                )
                                .padding(start = 15.dp, end = 15.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.scrim,
                                fontWeight = FontWeight.Bold
                                )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                if(selectedRepeatMode != "Daily"){
                    Text(
                        text = "Choose first notify date",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = date,
                        placeholder = {
                                Text(text = "Format is dd/MM/yyyy")
                        },
                        label = {
                            Text(text = "Date")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                        isError = dateError,
                        shape = RoundedCornerShape(15.dp),
                        singleLine = true,
                        onValueChange = { date = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = "Choose first notify time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier,
                    placeholder = {
                        Text(text = "Format is HH.MM")
                    },
                    label = {
                        Text(text = "Time")
                    },
                    isError = timeError,
                    value = time,
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                    onValueChange = { time = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {

                            val checkDateJob = launch { dateError = !Tools.checkDate(date) }
                            val checkTimeJob = launch { timeError = !Tools.checkTime(time) }
                            joinAll(checkDateJob, checkTimeJob)

                            if(!dateError && !timeError || selectedRepeatMode == "Daily" && !timeError){
                                val newTask = Task(
                                    animalId = animalId,
                                    title = title,
                                    description = description,
                                    date = date,
                                    time = time,
                                    repeating = repeatModes.getOrDefault(selectedRepeatMode, Task.ONE_TIME),
                                    type = taskTypes.getOrDefault(selectedTaskType, Task.OTHER)
                                )
                                Log.d("task", newTask.toString())
                                onAddTask(newTask)
                                onDismiss()
                            }

                        }

                              },
                    modifier= Modifier.align(Alignment.CenterHorizontally)

                ) {
                    Text(text = "Create")
                }
            }

        }
    }
}

