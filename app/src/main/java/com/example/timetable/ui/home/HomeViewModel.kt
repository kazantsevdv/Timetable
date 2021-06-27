package com.example.timetable.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetable.repo.TimetableRepo
import com.example.timetable.ui.classes.ClassesViewModel
import javax.inject.Inject
import javax.inject.Provider

class HomeViewModel @Inject constructor(private val repo: TimetableRepo): ViewModel()  {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<HomeViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == HomeViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }
}