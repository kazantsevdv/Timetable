package com.example.timetable.repo

import com.example.timetable.model.Classes
import java.util.*

interface TimetableRepo {
    suspend fun getClasses(date: Date):List<Classes>
}