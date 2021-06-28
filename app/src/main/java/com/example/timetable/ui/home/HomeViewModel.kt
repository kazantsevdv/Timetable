package com.example.timetable.ui.home

import androidx.lifecycle.*
import com.example.timetable.model.AppState
import com.example.timetable.model.Homework
import com.example.timetable.repo.TimetableRepo
import com.example.timetable.ui.model.DataItemClasses
import com.example.timetable.util.DateUtil
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class HomeViewModel @Inject constructor(
    private val repo: TimetableRepo,
    private val dateUtil: DateUtil
) : ViewModel() {

    private var _dataClasses = MutableLiveData<AppState<List<DataItemClasses>>>()
    var dataClasses: LiveData<AppState<List<DataItemClasses>>> = _dataClasses

    private var _dataHomework = MutableLiveData<AppState<List<Homework>>>()
    var dataHomework: LiveData<AppState<List<Homework>>> = _dataHomework

    private var _examDate = MutableLiveData<Long>()
    var examDate: LiveData<Long> = _examDate


    private var _activRowClasses = MutableLiveData<Int>()
    var activRowClasses: LiveData<Int> = _activRowClasses


    fun getClasses(data: Date) {




        _dataClasses.value = AppState.Loading(null)

        viewModelScope.launch {
            try {

                val newdata: MutableList<DataItemClasses> = mutableListOf()
                repo.getClasses(data).forEach {
                    val isActiv = dateUtil.isDatePeriod(it.timeStart, it.timeEnd)
                    if (isActiv) {
                        _activRowClasses.value = newdata.size
                    }

                    if (it.info.isEmpty()) {
                        newdata.add(
                            DataItemClasses.Classes(
                                it.timeStart, it.timeEnd,
                                it.theme, it.teacher, it.info, it.img, isActiv
                            )
                        )
                    } else {
                        newdata.add(
                            DataItemClasses.ClassesAdd(
                                it.timeStart, it.timeEnd,
                                it.theme, it.teacher, it.info, it.img, isActiv
                            )

                        )
                    }
                }

                _dataClasses.value = AppState.Success(newdata)

            } catch (exception: Exception) {
                _dataClasses.value = AppState.Error(exception)
            }
        }

        fun getHomework() {
            _dataHomework.value = AppState.Loading(null)
        }
        viewModelScope.launch {
            try {
                _dataHomework.value = AppState.Success(repo.getHomework())

            } catch (exception: Exception) {
                _dataHomework.value = AppState.Error(exception)
            }
        }

    }

    fun getHomework() {
        _dataHomework.value = AppState.Loading(null)

        viewModelScope.launch {
            try {
                _dataHomework.value = AppState.Success(repo.getHomework())

            } catch (exception: Exception) {
                _dataHomework.value = AppState.Error(exception)
            }
        }
    }

    fun getExamDate(){
        viewModelScope.launch {
           _examDate.value=dateUtil.dateToExam(repo.getExamDate())
        }
    }
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