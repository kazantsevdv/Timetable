package com.example.timetable.ui.classes

import androidx.lifecycle.*
import com.example.timetable.model.AppState
import com.example.timetable.repo.TimetableRepo
import com.example.timetable.ui.classes.model.DataItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ClassesViewModel @Inject constructor(private val repo: TimetableRepo) : ViewModel() {

    private var _data = MutableLiveData<AppState<List<DataItem>>>()
    var data: LiveData<AppState<List<DataItem>>> = _data
    private var _now = MutableLiveData<String>()
    var now: LiveData<String> = _now

    fun getClasses(data: Date) {


        _data.value = AppState.Loading(null)

        viewModelScope.launch {
            try {

                val newdata: MutableList<DataItem> = mutableListOf()
                repo.getClasses(data).forEach {
                    newdata.add(DataItem.Header(it.timeStart, it.timeEnd, false))
                    if (it.info.isNullOrEmpty()) {
                        newdata.add(
                            DataItem.Classes(
                                it.timeStart, it.timeEnd,
                                it.theme, it.teacher, it.info, it.img, false
                            )
                        )
                    } else {
                        newdata.add(
                            DataItem.ClassesAdd(
                                it.timeStart, it.timeEnd,
                                it.theme, it.teacher, it.info, it.img, true
                            )

                        )
                    }
                }

                _data.value = AppState.Success(newdata)

            } catch (exception: Exception) {
                _data.value = AppState.Error(exception)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<ClassesViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == ClassesViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }
}