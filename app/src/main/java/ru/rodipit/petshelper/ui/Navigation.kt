package ru.rodipit.petshelper.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import ru.rodipit.petshelper.viewModels.HelloViewModel
import ru.rodipit.petshelper.viewModels.MainViewModel
import ru.rodipit.petshelper.viewModels.SplashViewModel

object Navigation {

    const val SPLASH_ROUTE = "SPLASH_ROUTE"
    const val MAIN_ROUTE = "MAIN_ROUTE"
    const val HELLO_ROUTE = "HELLO_ROUTE"

    @Composable
    fun Navigation(){
        val navController = rememberNavController()

        NavHost(navController = navController,
            startDestination = SPLASH_ROUTE,
            enterTransition = { slideInHorizontally(initialOffsetX = { 300 }) + fadeIn() },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 300 }) + fadeOut() }
        ){
            navigation(startDestination = Screen.SplashScreen.name, route = SPLASH_ROUTE){
                composable(Screen.SplashScreen.name){
                    val viewModel: SplashViewModel = hiltViewModel()
                    SplashScreen(
                        navController = navController,
                        viewModel = viewModel
                        )
                }
            }
            navigation(startDestination = Screen.HelloScreen.name, route = HELLO_ROUTE){
                composable(Screen.HelloScreen.name){
                    val viewModel: HelloViewModel = hiltViewModel()
                    HelloScreen(
                        navController = navController,
                        viewModel = viewModel
                        )
                }
            }

            navigation(startDestination = Screen.MainFrame.name, route = MAIN_ROUTE){
                composable(Screen.MainFrame.name){
                    val viewModel: MainViewModel = hiltViewModel()
                    MainFrame(viewModel)
                }
            }
        }
    }

}