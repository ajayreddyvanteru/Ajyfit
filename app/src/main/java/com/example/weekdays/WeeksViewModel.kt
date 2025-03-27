package com.example.weekdays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeeksViewModel : ViewModel() {
    private val _weeksList = MutableLiveData<List<WeeksData>>() // or you could use Flow
    private val _selectedWeek = MutableLiveData<WeeksData>()
    val selectedWeek: LiveData<WeeksData> get() = _selectedWeek
    val weeksList: LiveData<List<WeeksData>> get() = _weeksList

    init {
        // Simulating data loading (you can replace this with actual data from a repository)
        _weeksList.value =  listOf(
            WeeksData("Monday", 1),
            WeeksData("Tuesday", 2),
            WeeksData("Wednesday", 3),
            WeeksData("Thursday", 3),
            WeeksData("Friday", 3),
            WeeksData("Saturday", 4),
            WeeksData("Sunday", 4)
        )
    }
    fun selectWeek(week: WeeksData) {
        _selectedWeek.value = week
    }
}