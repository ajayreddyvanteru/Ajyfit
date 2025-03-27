package com.example.roomDB


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.Exercises.ExerciseListViewModel
import com.example.roomDB.ExerciseInputDao

class ExerciseListViewModelFactory(private val userInputDao: ExerciseInputDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseListViewModel::class.java)) {
            return ExerciseListViewModel(Application(),userInputDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
