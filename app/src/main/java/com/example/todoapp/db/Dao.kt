package com.example.todoapp.db
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insert(task: Task)
    @Delete
    fun delete(task: Task)
    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<Task>>
}