package ru.rodipit.petshelper.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.rodipit.petshelper.presentation.ui.ui_states.MainScreenUiState

import ru.rodipit.petshelper.presentation.viewModels.EatingScreenViewModel

@Composable
fun EatingScreen(
    viewModel: EatingScreenViewModel,
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
    viewModel: EatingScreenViewModel,
    innerPadding: PaddingValues
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

    }
}