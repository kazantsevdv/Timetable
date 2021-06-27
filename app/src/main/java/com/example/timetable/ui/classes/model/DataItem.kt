package com.example.timetable.ui.classes.model

sealed class DataItem {
    data class Classes(
        val timeStart: String,
        val timeEnd: String,
        val theme: String,
        val teacher: String,
        val info: String,
        val img: String,
        var openIn: Boolean = false
    ) : DataItem()

    data class ClassesAdd(
        val timeStart: String,
        val timeEnd: String,
        val theme: String,
        val teacher: String,
        val info: String,
        val img: String,
        var openIn: Boolean = false
    ) : DataItem()

    data class Header(
        val timeStart: String,
        val timeEnd: String,
        val isActiv:Boolean=false
    ) : DataItem()
}