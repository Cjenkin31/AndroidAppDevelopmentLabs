package com.example.bitfitpart1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.bitfitpart1.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "bitfit-database"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchDataFromDatabase()
        setupRecordSleepButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SleepAdapter(listOf())
    }

    private fun fetchDataFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val sleepRecords = db.sleepDao().getAllSleepRecords()
            withContext(Dispatchers.Main) {
                (binding.recyclerView.adapter as SleepAdapter).updateData(sleepRecords)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        fetchDataFromDatabase()
    }
    private fun setupRecordSleepButton() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, RecordSleepActivity::class.java)
            startActivity(intent)
        }
    }
}
