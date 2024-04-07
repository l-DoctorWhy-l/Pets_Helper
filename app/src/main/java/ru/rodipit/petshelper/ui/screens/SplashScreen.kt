package ru.rodipit.petshelper.ui.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf


import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.core.LoadingStates
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.ui.Navigation
import ru.rodipit.petshelper.viewModels.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel
) {

    val visible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){

        launch {
            visible.value = true
            viewModel.isRegistered.collect { state ->
                println(state)
                if (state == LoadingStates.START){
                    delay(1000)
                    navController.navigate(Navigation.MAIN_ROUTE){popUpTo(Navigation.SPLASH_ROUTE)}
                } else if (state == LoadingStates.FAILED){
                    delay(1000)
                    navController.navigate(Navigation.HELLO_ROUTE){popUpTo(Navigation.SPLASH_ROUTE)}
                }
            }
        }

        launch {
            viewModel.checkCurrentUser()
        }

    }

    AnimatedVisibility(
        visible = visible.value,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally() + fadeOut()
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp
                )
            }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(
    navController: NavController = rememberNavController(),
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreen(navController = navController, viewModel = viewModel)
}