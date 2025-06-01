package com.example.todoapp.screens

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import com.example.todoapp.databinding.FragmentStatsBinding
import com.example.todoapp.db.MainDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StatsFragment : Fragment(R.layout.fragment_stats) {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentStatsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        val dao = MainDb.getDb(requireContext()).getDao()

        lifecycleScope.launch {
            val completed = withContext(Dispatchers.IO) { dao.getTodayCompletedCount() }
            val uncompleted = withContext(Dispatchers.IO) { dao.getTodayUncompletedCount() }

            binding.completedView.text = "Completed: $completed"
            binding.uncompletedView.text = "Uncompleted: $uncompleted"
        }
    }
}