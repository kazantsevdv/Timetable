package com.example.timetable.repo

import com.example.timetable.model.Classes
import com.example.timetable.model.Homework
import java.util.*

interface TimetableRepo {
    suspend fun getClasses(date: Date):List<Classes>
    suspend fun getHomework():List<Homework>
}