package com.example.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database()
abstract class MainDb: RoomDatabase() {
    companion object{
        fun getDb(context: Context): MainDb{
            return Room.databaseBuilder(context.applicationContext, MainDb::class.java, "main.db").build()
        }
    }

}