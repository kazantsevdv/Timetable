package com.example.timetable.repo

import com.example.timetable.model.Classes
import com.example.timetable.model.Homework
import kotlinx.coroutines.delay
import java.util.*

class TimetableRepoImpl:TimetableRepo {
    override suspend fun getClasses(date: Date): List<Classes> {
        delay(500)
        return arrayListOf(
            Classes("9:00","10:00","Истрия","Преподаватель Иванов","Тема урока Рим","file:///android_asset/a1.png"),
            Classes("10:00","11:00","Математика","Преподаватель Петров","","file:///android_asset/a1.png"),
            Classes("11:00","12:00","География","Преподаватель Иванов","Тема урока Страны","file:///android_asset/a1.png"),
            Classes("12:00","13:00","Истрия","Преподаватель Иванов","","file:///android_asset/a2.png"),
            Classes("13:00","14:00","Истрия","Преподаватель Иванов","","file:///android_asset/a1.png"),
            Classes("14:00","15:00","Истрия","Преподаватель Иванов","","file:///android_asset/a3.png"),
            Classes("15:00","16:00","Истрия","Преподаватель Иванов","","file:///android_asset/a2.png"),
            Classes("16:00","17:00","Истрия","Преподаватель Иванов","","file:///android_asset/a1.png"),
            Classes("17:00","18:00","Истрия","Преподаватель Иванов","","file:///android_asset/a2.png"),
            Classes("18:00","19:00","Истрия","Преподаватель Иванов","Доп занятие","file:///android_asset/a1.png"),

        )
    }

    override suspend fun getHomework(): List<Homework> {
       delay(500)
        return arrayListOf(
            Homework("01-07-2021","Истрия","Чтонибуть почитать","file:///android_asset/a2.png"),
            Homework("05-07-2021","География","Чтонибуть найти","file:///android_asset/a1.png"),
            Homework("07-07-2021","Труд","Чтонибуть сделать","file:///android_asset/a3.png"),
            Homework("09-07-2021","Математика","Чтонибуть посчитать","file:///android_asset/a1.png"),

        )
    }

    override suspend fun getExamDate(): String {
        return "01-07-2021"
    }
}