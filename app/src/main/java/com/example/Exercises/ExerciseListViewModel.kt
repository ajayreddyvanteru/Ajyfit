package com.example.Exercises

import androidx.lifecycle.ViewModel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.roomDB.AppDatabase
import com.example.roomDB.ExerciseInput
import com.example.roomDB.ExerciseInputDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseListViewModel(application: Application,private val userInputDao: ExerciseInputDao) : AndroidViewModel(application) {


    private val _mediaList = MutableLiveData<List<Pair<String, ByteArray>>>()
    val mediaList: LiveData<List<Pair<String, ByteArray>>> get() = _mediaList

    val userInputs = liveData(Dispatchers.IO) {
        emit(userInputDao.getAllInputs()) // Fetch the data from the Room database
    }

    // Insert function (you can call this from your fragment if needed)

    fun insertUserInput(inputText: String) {
        viewModelScope.launch {
            val userInput = ExerciseInput(inputText = inputText)
            userInputDao.insertUserInput(userInput) // Insert into the database
        }
    }

}
