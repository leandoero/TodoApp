package com.example.todoapp.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

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

        /*
Фрагмент создаётся один раз, и метод onViewCreated вызывается один раз при создании вида (view).
Внутри onViewCreated ты один раз устанавливаешь обработчик клика на кнопку fab:

fab.setOnClickListener {
    showAddTaskDialog()
}
Когда пользователь кликает на кнопку fab, вызывается именно этот обработчик — т.е. запускается функция showAddTaskDialog().
Это происходит каждый раз при клике, но сам фрагмент и его view не пересоздаётся.
        */

        val fab = view.findViewById<FloatingActionButton>(R.id.fabButton)
        val hintForUser = view.findViewById<TextView>(R.id.hintStart)
        fab.setOnClickListener {
            showAddTaskDialog(hintForUser)

        }

    }

    private fun showAddTaskDialog(hintForStart: TextView) {
        // 1. Создаём поле ввода
        val input = EditText(requireContext()).apply {
            hint = "Enter a task" // Подсказка в пустом поле
        }

        // 2. Создаём диалог
        AlertDialog.Builder(requireContext())
            .setTitle("Add a task") // Заголовок
            .setView(input) // Добавляем поле ввода в диалог
            .setPositiveButton("Add") { _, _ -> // Кнопка "Добавить"
                val task = input.text.toString() // Получаем текст
                if (task.isNotBlank()) { // Проверяем, что не пустой
                    taskList.add(task) // Добавляем в список
                    adapter.notifyItemInserted(taskList.size - 1) // Обновляем RecyclerView
                    updateHintStart(hintForStart)
                }
            }
            .setNegativeButton("Cancel", null) // Кнопка отмены (не делает ничего)
            .show() // Показываем диалог

        // 3. Настройка клавиатуры
        input.requestFocus() // Даём фокус полю ввода
        input.postDelayed({ // Ждём, пока диалог полностью откроется
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT) // Показываем клавиатуру
        }, 100)
    }
    private fun updateHintStart(hint: TextView){
        if(taskList.isEmpty()){
            hint.visibility = View.VISIBLE
        }
        else{
            hint.visibility = View.GONE
        }
    }
}