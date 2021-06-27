package com.example.timetable.di

import com.example.timetable.repo.TimetableRepo
import com.example.timetable.repo.TimetableRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideVideosRepo(): TimetableRepo = TimetableRepoImpl()

}