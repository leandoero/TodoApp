package com.example.todoapp.screens

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import com.example.todoapp.db.Dao
import com.example.todoapp.db.MainDb
import com.example.todoapp.db.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var adapter: MainAdapter
    private lateinit var db: MainDb
    private lateinit var dao: Dao
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        dao = db.getDao()

        checkAndClearTasksIfNewDay()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(mutableListOf()) { task ->
            lifecycleScope.launch(Dispatchers.IO) {
                dao.updateTaskCompleted(task.id, !task.isComplete)
            }
        }

        binding.recyclerView.adapter = adapter

        binding.fabButton.setOnClickListener {
            showAddTaskDialog(binding.hintStart)
        }

        lifecycleScope.launch {
            dao.getAll().collect { tasksList ->
                adapter.setData(tasksList)
                updateHintStart(binding.hintStart)
            }
        }
    }

    private fun showAddTaskDialog(hintForStart: TextView) {
        val input = EditText(requireContext()).apply {
            hint = "Enter a task"
        }
        AlertDialog.Builder(requireContext())
            .setTitle("Add a task")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val taskText = input.text.toString()
                if (taskText.isNotBlank()) {
                    val task = Task(null, taskText, false)
                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.insert(task)
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()

        input.requestFocus()
        input.postDelayed({
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
    }

    private fun updateHintStart(hint: TextView) {
        if (adapter.itemCount == 0) {
            hint.visibility = View.VISIBLE
        } else {
            hint.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkAndClearTasksIfNewDay() {
        val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastDate = prefs.getString("last_date", null)
        val todayDate = java.time.LocalDate.now().toString()  // формат "YYYY-MM-DD"

        if (lastDate != todayDate) {
            // новый день — очищаем задачи
            lifecycleScope.launch(Dispatchers.IO) {
                dao.deleteAllTasks()  // нужно добавить метод удаления всех задач в Dao
                prefs.edit().putString("last_date", todayDate).apply()
            }
        }
    }


}
