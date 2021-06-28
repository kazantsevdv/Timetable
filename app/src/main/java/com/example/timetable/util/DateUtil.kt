package com.example.timetable.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    fun getCurrentDate(format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }

    fun dateToExam(date:String):Long{
        val formatDate = SimpleDateFormat("dd-M-yyyy", Locale.getDefault())
        val examDate=formatDate.parse(date)
        val now=Date()
        return examDate.time-now.time
    }
    fun isDatePeriod(start: String, end: String): Boolean {

        val now = Calendar.getInstance(TimeZone.getDefault())
        val nowHour = now[Calendar.HOUR_OF_DAY]
        val nowMinute = now[Calendar.MINUTE]

        val formatDate = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startC = Calendar.getInstance(TimeZone.getDefault())
        startC.time = formatDate.parse(start)
        val startHour = startC[Calendar.HOUR_OF_DAY]
        val startMinute = startC[Calendar.MINUTE]
        val endC = Calendar.getInstance(TimeZone.getDefault())
        endC.time = formatDate.parse(end)
        val endHour = endC[Calendar.HOUR_OF_DAY]
        val endMinute = endC[Calendar.MINUTE]

        if (nowHour >= startHour && nowMinute >= startMinute && nowHour < endHour) {
            return true
        } else if (nowHour >= startHour && nowMinute >= startMinute && nowHour == endHour && nowMinute <= endMinute) {
            return true

        }
        return false
    }


}