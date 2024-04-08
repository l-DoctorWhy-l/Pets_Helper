package ru.rodipit.petshelper.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.rodipit.petshelper.presentation.ui.Navigation
import ru.rodipit.petshelper.presentation.ui.theme.PetsHelperTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetsHelperTheme {
                Navigation.Navigation()
            }
        }
    }
}