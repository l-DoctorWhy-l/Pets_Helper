package ru.rodipit.petshelper.presentation.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.core.Tools
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.presentation.ui.Navigation
import ru.rodipit.petshelper.presentation.viewModels.AddAnimalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnimalScreen(viewModel: AddAnimalViewModel, navController: NavController) {


    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var datePickerOpened by remember { mutableStateOf(false) }

    val dropDownMenuItems = listOf(
        "Cat",
        "Dog",
        "Fish",
        "Parrot",
        "Turtle",
        "Hamster",
        "Other"
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "New Pet",
            fontWeight = Bold,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.name,
            singleLine = true,
            maxLines = 1,
            label = { Text(text = stringResource(id = R.string.enter_pets_nickname)) },
            onValueChange = {
                viewModel.changeName(it)
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter the type",
            fontWeight = Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                value = AnimalEntity.castAnimalTypeFromIntToString(uiState.animalType),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                dropDownMenuItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            viewModel.changeAnimalType(
                                AnimalEntity.castAnimalTypeFromStringToInt(
                                    item
                                )
                            )
                            expanded = false
                        }
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.fullName,
            singleLine = true,
            maxLines = 1,
            label = { Text(text = stringResource(id = R.string.enter_pets_fullname)) },
            onValueChange = {
                viewModel.changeFullname(it)
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
            if (uiState.bDay == "") {
                stringResource(id = R.string.enter_b_day)
            } else {
                uiState.bDay
            },
            fontSize = 24.sp,
            modifier = Modifier
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(14.dp)
                .clickable(onClick = {
                    datePickerOpened = true
                })
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.breed,
            singleLine = true,
            maxLines = 1,
            label = { Text(text = stringResource(id = R.string.enter_breed)) },
            onValueChange = {
                viewModel.changeBreed(it)
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                viewModel.createNewAnimal()
            },
            Modifier.fillMaxWidth(0.5f)
        ) {
            Text(text = stringResource(id = R.string.done), fontWeight = Bold)
        }
    }

    if (datePickerOpened) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { datePickerOpened = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerOpened = false
                        var date = "Change birthdate"
                        if (datePickerState.selectedDateMillis != null) {
                            date = Tools.convertLongToDate(datePickerState.selectedDateMillis!!)
                        }
                        viewModel.changeBirthDate(date)
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(text = stringResource(id = R.string.okay))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.uiState.collect {
                println(it)
                if (it.isSuccess) {
                    navController.navigate(Navigation.MAIN_ROUTE) { popUpTo(Navigation.ADD_ANIMAL_ROUTE) }
                }
            }
        }
    }

}
