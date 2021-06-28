package com.example.timetable.di

import com.example.timetable.App
import com.example.timetable.util.DateUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {


    @Singleton
    @Provides
    fun app(): App {
        return app
    }

    @Singleton
    @Provides
    fun provideDateUtil(): DateUtil = DateUtil()
}