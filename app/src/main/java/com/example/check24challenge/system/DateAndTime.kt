package com.example.check24challenge.system

import java.text.SimpleDateFormat
import java.util.*

class DateAndTime {
    companion object{
        fun convertLongToStringTimeStampUTC(time: Long): String? { // todo sample output 2021.02.06
            val format = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(Date(time * 1000))
        }
    }
}