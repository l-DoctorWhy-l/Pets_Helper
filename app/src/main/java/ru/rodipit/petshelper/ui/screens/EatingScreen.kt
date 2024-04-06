package ru.rodipit.petshelper.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.rodipit.petshelper.viewModels.MainScreenViewModel

@Composable
fun EatingScreen() {

//    val animal by viewModel.animal.collectAsState()


    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "EatingScreen ")
    }
}