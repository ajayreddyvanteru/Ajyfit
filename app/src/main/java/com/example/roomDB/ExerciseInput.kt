package com.example.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "exercise_input")
data class ExerciseInput(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val uri: String
)

@Entity(tableName = "exercise_input_text")
data class ExerciseInputText(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val muscle: String
): Serializable
