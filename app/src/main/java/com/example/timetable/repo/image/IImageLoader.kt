package com.example.timetable.repo.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}