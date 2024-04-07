package ru.rodipit.petshelper.activities

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import ru.rodipit.petshelper.ui.Navigation
import ru.rodipit.petshelper.ui.theme.PetsHelperTheme


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