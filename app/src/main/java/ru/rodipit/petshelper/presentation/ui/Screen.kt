package ru.rodipit.petshelper.presentation.ui

import ru.rodipit.petshelper.R

sealed class Screen(val name: String, val iconId: Int = -1, val label: String = "") {

    data object MainScreen : Screen("MAIN_SCREEN", R.drawable.baseline_pets_24, "Pet")
    data object AddAnimalScreen : Screen("ADD_ANIMAL_SCREEN")
    data object HelloScreen : Screen("HELLO_SCREEN")
    data object EatingScreen : Screen("EATING_SCREEN", R.drawable.baseline_lunch_dining_24, "Eating")
    data object ExpensesScreen : Screen("EXPENSES_SCREEN", R.drawable.baseline_monetization_on_24, "Expenses")
    data object MedicineScreen : Screen("MEDICINE_SCREEN", R.drawable.baseline_healing_24, "Medicine")
    data object PetProfileScreen : Screen("PET_PROFILE_SCREEN")
    data object SplashScreen : Screen("SPLASH_SCREEN")
    data object MainFrame : Screen("MAIN_FRAME")

}