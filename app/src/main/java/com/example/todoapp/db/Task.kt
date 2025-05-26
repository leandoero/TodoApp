package com.example.todoapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val task:String,
    val isComplete: Boolean
)
