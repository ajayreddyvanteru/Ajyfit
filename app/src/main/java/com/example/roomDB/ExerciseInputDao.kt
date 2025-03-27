package com.example.roomDB


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseInputDao {
    @Insert
    suspend fun insertUserInput(exerciseInput: ExerciseInput) // Method to insert data

    @Query("SELECT * FROM exercise_input")
    suspend fun getAllInputs(): List<ExerciseInput> // Method to get all inputs from the table
}
