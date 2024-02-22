package ru.rodipit.petshelper.models

interface Animal {







    companion object{
        const val CAT = 1
        const val DOG = 2
        const val FISH = 3
        const val PARROT = 4
        const val TURTLE = 5
        const val HAMSTER = 6
        const val OTHER = 0


        fun castAnimalType(animalType: String) = when(animalType) {
                "Cat" -> CAT
                "Dog" -> DOG
                "Fish" -> FISH
                "Parrot" -> PARROT
                "Turtle" -> TURTLE
                "Hamster" -> HAMSTER
                "Other" -> OTHER
                else -> OTHER
            }

    }

}