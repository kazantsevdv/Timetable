package com.example.timetable.ui.classes

import androidx.lifecycle.*
import com.example.timetable.model.AppState
import com.example.timetable.repo.TimetableRepo
import com.example.timetable.ui.model.DataItemClasses
import com.example.timetable.util.DateUtil
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ClassesViewModel @Inject constructor(
    private val repo: TimetableRepo,
    private val dateUtil: DateUtil
) : ViewModel() {

    private var _data = MutableLiveData<AppState<List<DataItemClasses>>>()
    var data: LiveData<AppState<List<DataItemClasses>>> = _data
    private var _now = MutableLiveData<String>()
    var now: LiveData<String> = _now
    private var _activRow = MutableLiveData<Int>()
    var activRow: LiveData<Int> = _activRow


    fun getClasses(data: Date) {

        _now.value = dateUtil.getCurrentDate("dd MMMM")


        _data.value = AppState.Loading(null)

        viewModelScope.launch {
            try {

                val newdata: MutableList<DataItemClasses> = mutableListOf()
                repo.getClasses(data).forEach {
                    val isActiv = dateUtil.isDatePeriod(it.timeStart, it.timeEnd)
                    if (isActiv) {
                        _activRow.value = newdata.size
                    }
                    newdata.add(
                        DataItemClasses.Header(
                            it.timeStart,
                            it.timeEnd,
                            isActiv
                        )
                    )

                    if (it.info.isNullOrEmpty()) {
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