package com.example.Exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomDB.AppDatabase
import com.example.roomDB.ExerciseInput
import com.example.roomDB.ExerciseInputDao
import com.example.roomDB.ExerciseInputText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseListViewModel(application: Application) : AndroidViewModel(application) {

    private val imageDao: ExerciseInputDao = AppDatabase.getDatabase(application).imageDao()

    private val _exercisesImages = MutableLiveData<List<ExerciseInput>>()
    val allImages: LiveData<List<ExerciseInput>> = _exercisesImages
    private var _musclesGroupData = MutableLiveData<List<ExerciseInputText>>()
    val allTexts: LiveData<List<ExerciseInputText>> = _musclesGroupData
    fun saveImage(image: ExerciseInput) {
        viewModelScope.launch(Dispatchers.IO) {
            imageDao.insertImage(image)
        }
    }
    fun saveTexts(text: ExerciseInputText, group: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageDao.insertExerciseText(text)
            getexercises(group)
        }
    }
    fun getexercises(muscle: String) {
        viewModelScope.launch {
            val exercises = withContext(Dispatchers.IO) {
                imageDao.getAllTextsLive(muscle)
            }
            _musclesGroupData.value = exercises
        }
    }
    fun getmedia(muscle:String){
        viewModelScope.launch {
            val exercises = withContext(Dispatchers.IO) {
                imageDao.getAllImagesLive(muscle)
            }
            _exercisesImages.value = exercises
        }
    }
}
