package com.example.roomDB


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseInputDao {
    @Insert
    suspend fun insertImage(image: ExerciseInput)

    @Query("SELECT * FROM exercise_input")
    fun getAllImagesLive(): LiveData<List<ExerciseInput>>
}
