package com.example.todoapp.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment: Fragment(R.layout.fragment_main) {

    //lateinit — "поздняя инициализация"
    private lateinit var adapter: MainAdapter
    private val taskList = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(taskList)
        recyclerView.adapter = adapter


        val fab = view.findViewById<FloatingActionButton>(R.id.fabButton)
        fab.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        // 1. Создаём поле ввода
        val input = EditText(requireContext()).apply {
            hint = "Введите задачу" // Подсказка в пустом поле
        }

        // 2. Создаём диалог
        AlertDialog.Builder(requireContext())
            .setTitle("Добавить задачу") // Заголовок
            .setView(input) // Добавляем поле ввода в диалог
            .setPositiveButton("Добавить") { _, _ -> // Кнопка "Добавить"
                val task = input.text.toString() // Получаем текст
                if (task.isNotBlank()) { // Проверяем, что не пустой
                    taskList.add(task) // Добавляем в список
                    adapter.notifyItemInserted(taskList.size - 1) // Обновляем RecyclerView
                }
            }
            .setNegativeButton("Отмена", null) // Кнопка отмены (не делает ничего)
            .show() // Показываем диалог

        // 3. Настройка клавиатуры
        input.requestFocus() // Даём фокус полю ввода
        input.postDelayed({ // Ждём, пока диалог полностью откроется
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT) // Показываем клавиатуру
        }, 100)
    }
}