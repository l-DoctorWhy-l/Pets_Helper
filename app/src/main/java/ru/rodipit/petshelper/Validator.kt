package ru.rodipit.petshelper


import java.util.GregorianCalendar
import java.util.StringTokenizer

class Validator {
    companion object{
        fun validateName(name: String): Boolean{
            return name.length in 1..20
        }
        fun validateFullname(name: String): Boolean{
            return name.length in 1..40
        }

        fun validateBreed(breed: String): Boolean{
            return breed.length in 1..40
        }

        fun validateDate(date: String): Long{
            val dateArray = arrayListOf<String>()
            try {
                val tokenizer = StringTokenizer(date, "./ -");
                while(tokenizer.hasMoreElements()){
                    dateArray.add(tokenizer.nextToken());
                }
                if(dateArray.size != 3 || dateArray[0].length !in 1..2 || dateArray[1].length !in 1..2 || dateArray[2].length !in arrayOf(2,4))
                    return 0

            } catch (e: Exception){
                println(e)
                return 0
            }
            val dateD = GregorianCalendar(dateArray[2].toInt(), dateArray[1].toInt() - 1, dateArray[0].toInt())
            return dateD.timeInMillis
        }

    }
}