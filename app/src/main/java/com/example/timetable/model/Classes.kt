package com.example.timetable.model

data class Classes(
    val timeStart: String,
    val timeEnd: String,
    val theme: String,
    val teacher: String,
    val info: String,
    val img: String,
    var openIn: Boolean = false
)