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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupRecordSleepButton()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.sleep_list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SleepListFragment.newInstance())
                        .commit()
                    true
                }
                R.id.sleep_dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SleepAverageFragment.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecordSleepButton() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, RecordSleepActivity::class.java)
            startActivity(intent)
        }
    }
}

