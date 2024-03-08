package com.nothing.societyuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nothing.societyuser.Adapter.ComplainHistoryAdapter
import com.nothing.societyuser.Model.complainHistoryModel
import com.nothing.societyuser.databinding.ActivityComplainRaiseHistoryBinding
import java.util.*

class ComplainRaiseHistory : AppCompatActivity() {
    private lateinit var binding: ActivityComplainRaiseHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainRaiseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Dummy data for testing
        val dummyData = listOf(
            complainHistoryModel(
                R.drawable.ic_launcher_foreground,
                "WatchMan",
                "Elevator Not Working",
                Date(),
                "Complete",
                "Something issue but I don't understand what the issue is."
            ),
            complainHistoryModel(
                R.drawable.ic_launcher_foreground,
                "Maintenance",
                "Leaky Faucet",
                Date(),
                "Pending",
                "Water leaking from the faucet in the kitchen."
            ),
            // Add more dummy data as needed
        )

        // Initialize RecyclerView and set up the adapter

        binding.complainRaiseHistoryRv.layoutManager = LinearLayoutManager(this)
        val adapter = ComplainHistoryAdapter(dummyData)
        binding.complainRaiseHistoryRv.adapter = adapter
    }
}
