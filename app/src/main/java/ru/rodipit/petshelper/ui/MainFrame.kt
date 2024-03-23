package ru.rodipit.petshelper.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.models.Animal
import ru.rodipit.petshelper.viewModels.MainScreenViewModel
import ru.rodipit.petshelper.viewModels.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFrame(viewModel: MainViewModel){

    val uiState by viewModel.uiState.collectAsState()


    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Row(modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        IconButton(onClick = { viewModel.previousAnimal() },
                            modifier = Modifier.weight(0.1f,true),
                            enabled = uiState.currentAnimalPosition != 0
                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_circle_left_24),
                                contentDescription = "Previous",
                                modifier = Modifier.fillMaxSize()
                                )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        if(uiState.currentAnimalPosition == uiState.animals.size - 1){
                            AddAnimalItem(modifier = Modifier.weight(0.7f, true))
                        } else{
                            AnimalItem(
                                animal = uiState.animals[uiState.currentAnimalPosition],
                                modifier = Modifier.weight(0.7f, true)
                            )

                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = { viewModel.nextAnimal() },
                            modifier = Modifier.weight(0.1f),
                            enabled = uiState.currentAnimalPosition < uiState.animals.size - 1
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_circle_right_24),
                                contentDescription = "Next",
                                modifier = Modifier.fillMaxSize()
                                )
                        }
                    }
                }
            )
        },
        bottomBar = {
            val items = listOf(Screen.MainScreen, Screen.EatingScreen, Screen.ExpensesScreen, Screen.MedicineScreen)
            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach{item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.name } == true,
                        icon = { Icon(
                            painterResource(id = item.iconId),
                            contentDescription = item.name,
                            modifier = Modifier.size(30.dp)
                            )
                               },
                        label = { Text(text = item.label)},
                        onClick = {

                            navController.navigate(item.name){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    )

                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.MainScreen.name){
            composable(
                route = Screen.MainScreen.name,
                arguments = listOf(
                    navArgument("animalId"){
                        type = NavType.IntType
                    }
                )

            ){
                val viewModel: MainScreenViewModel = hiltViewModel()
                val animalId = it.arguments?.getInt("animalId") ?: -1
                viewModel.loadAnimal(animalId)
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun AnimalItem(animal: AnimalEntity = AnimalEntity(), modifier: Modifier){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = getAnimalImgId(animal)),
            contentDescription = "Img",
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = animal.name,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1
        )
    }
}
@Composable
fun AddAnimalItem(modifier: Modifier){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_add_circle_24),
            contentDescription = "Add",
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Add Pet",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1
        )
    }
}

fun getAnimalImgId(animal: AnimalEntity) =  when(animal.type){
    Animal.DOG -> R.drawable.dog
    Animal.FISH -> R.drawable.fish
    Animal.HAMSTER -> R.drawable.hamster
    Animal.PARROT -> R.drawable.parrot
    Animal.TURTLE -> R.drawable.turtle
    Animal.CAT -> R.drawable.cat
    Animal.OTHER -> R.drawable.pow
    else -> R.drawable.pow
}