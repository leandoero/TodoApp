package com.example.todoapp.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class MainAdapter(private val tasks: MutableList<String>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.taskView)
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
        holder.textView.text = tasks[position]

        /*
        удалять нужно через адаптер, потому что:
        адаптер управляет данными, которые показываются в RecyclerView
        RecyclerView ничего не знает о списке задач напрямую — он получает всё только от адаптера.
         */
        holder.textView.setOnClickListener {
            tasks.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = tasks.size

}