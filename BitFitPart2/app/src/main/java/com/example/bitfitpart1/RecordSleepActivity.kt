package com.example.bitfitpart1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.bitfitpart1.R
import com.example.bitfitpart1.databinding.InputForumBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordSleepActivity : AppCompatActivity() {

    private lateinit var binding: InputForumBinding
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "bitfit-database"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InputForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val sleepName = binding.sleepInput.text.toString()
            val sleepDate = binding.sleepDate.text.toString()

            if (sleepName.isNotEmpty() && sleepDate.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val hours = sleepName.toInt()
                        db.sleepDao().insertAll(SleepRecord(date = sleepDate, hours = hours))
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Record added successfully", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    } catch (e: Exception) {
                        // Log the exception
                        Log.e("RecordSleepActivity", "Failed to add record", e)
                    }


                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
