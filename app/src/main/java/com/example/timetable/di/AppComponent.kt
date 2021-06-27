package com.example.timetable.di

import com.example.timetable.ui.classes.ClassesFragment
import com.example.timetable.ui.home.HomeFragment

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        RepoModule::class,
        GlideModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ClassesFragment)

}