package ru.rodipit.petshelper.presentation.ui

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
import androidx.compose.foundation.magnifier
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import ru.rodipit.petshelper.presentation.ui.theme.PetsHelperTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.R
import ru.rodipit.petshelper.data.entities.AnimalEntity
import ru.rodipit.petshelper.presentation.ui.screens.EatingScreen
import ru.rodipit.petshelper.presentation.ui.screens.MainScreen
import ru.rodipit.petshelper.presentation.viewModels.MainScreenViewModel
import ru.rodipit.petshelper.presentation.viewModels.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFrame(viewModel: MainViewModel, mainNavController: NavController){

    val uiState by viewModel.uiState.collectAsState()

    val navController = rememberNavController()
    var currentScreen by remember {
        mutableStateOf(Screen.MainScreen.name)
    }

    LaunchedEffect(Unit){
        launch {
            viewModel.uiState.collect{
                navigateWithArg(navController, currentScreen, it.animals[it.currentAnimalPosition].id)
            }
        }
    }


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
                                painter = painterResource(
                                    id = R.drawable.baseline_arrow_circle_left_24
                                ),
                                contentDescription = "Previous",
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        if(uiState.currentAnimalPosition == uiState.animals.size - 1){
                            AddAnimalItem(modifier = Modifier.weight(0.7f, true),
                                navController = mainNavController
                                )
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
                                painter = painterResource(
                                    id = R.drawable.baseline_arrow_circle_right_24
                                ),
                                contentDescription = "Next",
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                        }
                    }
                }
            )
        },
        bottomBar = {
            val items = listOf(
                Screen.MainScreen,
                Screen.EatingScreen,
                Screen.ExpensesScreen,
                Screen.MedicineScreen
            )
            NavigationBar(

            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach{item ->
                    NavigationBarItem(
                        selected =  currentDestination?.hierarchy?.any {
                            if(it.route?.indexOf("/") == -1){
                                it.route == item.name
                            } else{
                                it.route?.substring(0, it.route!!.indexOf("/")) == item.name
                            }
                        } == true,
                        icon = { Icon(
                            painterResource(id = item.iconId),
                            contentDescription = item.name,
                            modifier = Modifier.size(30.dp)
                            )
                               },
                        label = { Text(text = item.label)},
                        onClick = {
                            println(item.name + "/${uiState.animals[uiState.currentAnimalPosition].id}")
                            navigateWithArg(navController, item.name, uiState.animals[uiState.currentAnimalPosition].id)
                        }
                    )

                }
            }
        }
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MainScreen.name + "/${uiState.animals[0].id}"
        )
        {
            composable(
                route = Screen.MainScreen.name + "/{animalId}",
                arguments = listOf(
                    navArgument("animalId"){
                        type = NavType.IntType
                        defaultValue = uiState.animals[0].id
                    }
                )

            ){backStack ->
                currentScreen = Screen.MainScreen.name
                val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
                val animalId = backStack.arguments?.getInt("animalId") ?: -1
                MainScreen(mainScreenViewModel, animalId, innerPadding)
            }
            composable(
                route = Screen.EatingScreen.name + "/{animalId}",
                arguments = listOf(
                    navArgument("animalId"){
                        type = NavType.IntType
                        defaultValue = uiState.animals[0].id
                    }
                )

            ){
                currentScreen = Screen.EatingScreen.name
//                val eatingScreenViewModel: EatingScreenViewModel = hiltViewModel()
                LaunchedEffect(Unit){
                    val animalId = it.arguments?.getInt("animalId") ?: -1
//                    eatingScreenViewModel.loadAnimal(animalId)
                }
                EatingScreen()
            }
        }
    }
}

@Composable
fun AnimalItem(animal: AnimalEntity = AnimalEntity(), modifier: Modifier){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
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
                .background(MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = animal.name,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            maxLines = 1
        )
    }
}
@Composable
fun AddAnimalItem(modifier: Modifier, navController: NavController){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
            .clickable { navController.navigate(Navigation.ADD_ANIMAL_ROUTE) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_add_circle_24),
            contentDescription = "Add",
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp)
                .background(MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Add Pet",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            maxLines = 1
        )
    }
}


fun navigateWithArg(navController: NavController, route: String, arg: Int?){
    navController.navigate(
        "$route/${arg}"
    )
    {
        restoreState = true

        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }

    }
}

fun getAnimalImgId(animal: AnimalEntity) =  when(animal.type){
    AnimalEntity.DOG -> R.drawable.dog
    AnimalEntity.FISH -> R.drawable.fish
    AnimalEntity.HAMSTER -> R.drawable.hamster
    AnimalEntity.PARROT -> R.drawable.parrot
    AnimalEntity.TURTLE -> R.drawable.turtle
    AnimalEntity.CAT -> R.drawable.cat
    AnimalEntity.OTHER -> R.drawable.pow
    else -> R.drawable.pow
}
