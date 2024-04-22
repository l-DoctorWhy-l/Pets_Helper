package ru.rodipit.petshelper.core

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class Tools {

    companion object{
        @SuppressLint("SimpleDateFormat")
        fun convertLongToDate(timeLong: Long): String{
            val date = Date(timeLong)
            val format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun convertDateToLong(date: String): Long{
            val f = SimpleDateFormat("dd/MM/yyyy")
            var millis = 0L
            try {
                val d = f.parse(date)
                if (d != null) {
                    millis = d.time
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return millis
        }


        @SuppressLint("SimpleDateFormat")
        fun convertTimeToLong(time: String): Long{
            val f = SimpleDateFormat("H:m")
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

        @SuppressLint("SimpleDateFormat")
        fun convertLongToTime(timeLong: String): Long{
            val f = SimpleDateFormat("H:m")
            var millis = 0L
            try {
                val d = f.parse(timeLong)
                if (d != null) {
                    millis = d.time
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return millis
        }


        @SuppressLint("SimpleDateFormat")
        fun checkTime(time: String): Boolean{
            val format = SimpleDateFormat("H:m")

            return try {
                format.parse(time)
                true
            } catch (e: ParseException){
                false
            }

        }


        @SuppressLint("SimpleDateFormat")
        fun checkDate(date: String): Boolean{
            val format = SimpleDateFormat("dd/MM/yyyy")
            return try {
                format.parse(date)
                true
            } catch(e: ParseException){
                false
            }
        }


    }




}


