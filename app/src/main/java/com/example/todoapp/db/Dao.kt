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

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<Task>>
    @Query("UPDATE tasks SET task_complete = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompleted(taskId: Int?, isCompleted: Boolean)

    @Query("SELECT COUNT(*) FROM tasks WHERE task_complete = 1 AND date(create_time / 1000, 'unixepoch') = date('now')")
    suspend fun getTodayCompletedCount(): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE task_complete = 0 AND date(create_time / 1000, 'unixepoch') = date('now')")
    suspend fun getTodayUncompletedCount(): Int

}