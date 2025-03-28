package com.example.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_input")
data class ExerciseInput(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val uri: String
)
