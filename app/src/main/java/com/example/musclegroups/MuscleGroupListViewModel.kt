package com.example.musclegroups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weekdays.WeeksData

class MuscleGroupListViewModel : ViewModel() {
    private val _musclesGroupData = MutableLiveData<List<MuscleGroupData>>()
    val musclesGroupData: LiveData<List<MuscleGroupData>> get() = _musclesGroupData

    fun updateSelectedWeekData(selectedWeek: WeeksData) {
        when(selectedWeek.Day){
            "Monday","Thursday"->{
                _musclesGroupData.value= listOf(
                    MuscleGroupData("Chest"),
                    MuscleGroupData("Tricep")
                )
            }
            "Tuesday","Friday"->{
                _musclesGroupData.value= listOf(
                    MuscleGroupData("Back"),
                    MuscleGroupData("Bicep")
                )
            }
            "Wednesday","Saturday"->{
                _musclesGroupData.value= listOf(
                    MuscleGroupData("Sholders"),
                    MuscleGroupData("Legs")
                )
            }
        }
    }

    // Example function to fetch data for the selected week

}