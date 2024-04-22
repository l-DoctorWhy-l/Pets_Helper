package ru.rodipit.petshelper.presentation.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.rodipit.petshelper.presentation.ui.ui_states.MainScreenUiState
import ru.rodipit.petshelper.presentation.viewModels.MedicineScreenViewModel

@Composable
fun MedicineScreen(
    viewModel: MedicineScreenViewModel,
    animalId: Int,
    innerPadding: PaddingValues,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadAnimal(animalId)
    }


    if (animalId != -1) {
        AnimalScreen(uiState = uiState, viewModel, innerPadding = innerPadding)
    } else {
        NeedAnimalScreen(innerPadding = innerPadding)
    }

}
@Composable
fun AnimalScreen(
    uiState: MainScreenUiState,
    viewModel: MedicineScreenViewModel,
    innerPadding: PaddingValues
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Coming soon...",
            modifier = Modifier.align(Alignment.CenterHorizontally)
            )
    }
}