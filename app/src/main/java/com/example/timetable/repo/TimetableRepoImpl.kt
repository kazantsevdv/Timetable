package com.example.timetable.repo

import com.example.timetable.model.Classes
import kotlinx.coroutines.delay
import java.util.*

class TimetableRepoImpl:TimetableRepo {
    override suspend fun getClasses(date: Date): List<Classes> {
        delay(500)
        return arrayListOf(
            Classes(0,"9:00","10:00","Истрия","Преподаватель Иванов","Тема урока Рим","file:///android_asset/a1.png"),
            Classes(0,"10:00","11:00","Математика","Преподаватель Петров","","file:///android_asset/a1.png"),
            Classes(0,"11:00","12:00","География","Преподаватель Иванов","Тема урока Страны","file:///android_asset/a1.png"),
            Classes(0,"12:00","13:00","Истрия","Преподаватель Иванов","","file:///android_asset/a2.png"),
            Classes(0,"13:00","14:00","Истрия","Преподаватель Иванов","","file:///android_asset/a1.png"),
            Classes(0,"14:00","15:00","Истрия","Преподаватель Иванов","","file:///android_asset/a3.png"),
            Classes(0,"15:00","16:00","Истрия","Преподаватель Иванов","","file:///android_asset/a2.png"),
            Classes(0,"16:00","17:00","Истрия","Преподаватель Иванов","","file:///android_asset/a1.png"),
            Classes(0,"17:00","18:00","Истрия","Преподаватель Иванов","","file:///android_asset/a2.png"),
            Classes(0,"18:00","19:00","Истрия","Преподаватель Иванов","Доп занятие","file:///android_asset/a1.png"),

        )
    }
}