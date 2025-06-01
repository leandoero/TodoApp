package com.example.todoapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    @ColumnInfo(name = "task_name")
    val task:String,
    @ColumnInfo(name = "task_complete")
    val isComplete: Boolean,
    @ColumnInfo(name = "create_time")
    val createdAt: Long = System.currentTimeMillis()
)
