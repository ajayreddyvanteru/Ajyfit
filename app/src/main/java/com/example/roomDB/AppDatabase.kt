package com.example.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExerciseInput::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInputDao(): ExerciseInputDao // Provide access to the DAO
}
