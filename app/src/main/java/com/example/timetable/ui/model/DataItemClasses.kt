package com.example.timetable.ui.model

sealed class DataItemClasses {
    data class Classes(
        val timeStart: String,
        val timeEnd: String,
        val theme: String,
        val teacher: String,
        val info: String,
        val img: String,
        var openIn: Boolean = false
    ) : DataItemClasses()

    data class ClassesAdd(
        val timeStart: String,
        val timeEnd: String,
        val theme: String,
        val teacher: String,
        val info: String,
        val img: String,
        var openIn: Boolean = false
    ) : DataItemClasses()

    data class Header(
        val timeStart: String,
        val timeEnd: String,
        val isActiv:Boolean=false
    ) : DataItemClasses()
}