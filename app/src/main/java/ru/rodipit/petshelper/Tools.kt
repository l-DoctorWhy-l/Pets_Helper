package ru.rodipit.petshelper

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class Tools {

    companion object{
        @SuppressLint("SimpleDateFormat")
        fun convertLongToTime(time: Long): String{
            val date = Date(time)
            val format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun convertTimeToLong(time: String): Long{
            val f = SimpleDateFormat("dd/MM/yyyy")
            var millis = 0L
            try {
                val d = f.parse(time)
                if (d != null) {
                    millis = d.time
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return millis
        }
    }



}