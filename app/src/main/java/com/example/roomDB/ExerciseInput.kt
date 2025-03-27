package com.example.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_input")
data class ExerciseInput(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-incrementing ID
    val inputText: String // Text that you want to insert
)
