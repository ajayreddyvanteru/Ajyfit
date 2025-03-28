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

class ExerciseListViewModel(application: Application) : AndroidViewModel(application) {

    private val imageDao: ExerciseInputDao = AppDatabase.getDatabase(application).imageDao()
    val allImages: LiveData<List<ExerciseInput>> = imageDao.getAllImagesLive()

    fun saveImage(image: ExerciseInput) {
        viewModelScope.launch(Dispatchers.IO) {
            imageDao.insertImage(image)
        }
    }
}
