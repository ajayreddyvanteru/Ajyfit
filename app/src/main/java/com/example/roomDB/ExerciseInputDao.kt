package com.example.roomDB


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseInputDao {
    @Insert
    suspend fun insertImage(image: ExerciseInput)

    @Query("SELECT * FROM exercise_input Where name=:name")
    fun getAllImagesLive(name: String): List<ExerciseInput>

    @Insert
    suspend fun insertExerciseText(texts: ExerciseInputText)

    @Query("SELECT * FROM exercise_input_text Where muscle = :muscle")
    fun getAllTextsLive(muscle: String): List<ExerciseInputText>
}
