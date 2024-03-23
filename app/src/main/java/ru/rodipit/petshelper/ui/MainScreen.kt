package ru.rodipit.petshelper.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.rodipit.petshelper.viewModels.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {

    val animal by viewModel.animal.collectAsState()


    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ){
            Text(text = "MainScreen $animal")
    }
}