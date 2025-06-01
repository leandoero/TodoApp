package com.example.todoapp.screens

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.db.Task
import kotlin.and
import kotlin.or

class MainAdapter(private val tasks: MutableList<Task>,
                  private val onTaskClick: (Task) -> Unit // простой колбэк
    ): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.taskView)
        fun bind(task: Task) {
            textView.text = task.task
            if (task.isComplete) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                textView.alpha = 0.5f
            } else {
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                textView.alpha = 1f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_simple, parent, false)
        return ViewHolder(view)
    }

    /*
    RecyclerView спрашивает: «Сколько задач?» → getItemCount()
    Создаёт нужное количество ViewHolder через onCreateViewHolder
    Привязывает данные к ViewHolder'ам через onBindViewHolder
    Когда пользователь кликает по элементу:
    задача удаляется
    вызывается notifyDataSetChanged()
    RecyclerView снова всё обновляет
    */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
        holder.textView.setOnClickListener {
            onTaskClick(task) // передаём задачу наружу
        }
    }

    override fun getItemCount() = tasks.size

    fun setData(newList: List<Task>) {
        tasks.clear()
        tasks.addAll(newList)
        notifyDataSetChanged()
    }



}