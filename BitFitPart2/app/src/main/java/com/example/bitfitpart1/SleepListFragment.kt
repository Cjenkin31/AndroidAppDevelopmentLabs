package com.example.bitfitpart1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bitfitpart1.databinding.FragmentSleepListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SleepListFragment"
class SleepListFragment : Fragment() {
    private val db by lazy {
        Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "bitfit-database"
        ).build()
    }
    private var _binding: FragmentSleepListBinding? = null
    private val binding get() = _binding!!
    private val articles = mutableListOf<SleepRecord>()
    private lateinit var sleepRecyclerView: RecyclerView
    private lateinit var sleepAdapter: SleepAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSleepListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchDataFromDatabase()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        sleepAdapter = SleepAdapter(emptyList()) // Initialize with empty list
        binding.recyclerView.adapter = sleepAdapter
    }

    private fun fetchDataFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Fetching sleep records from database...")
            try {
                val sleepRecords = db.sleepDao().getAllSleepRecords()
                Log.d(TAG, "Fetched ${sleepRecords.size} sleep records.")
                withContext(Dispatchers.Main) {
                    sleepAdapter.updateData(sleepRecords)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching sleep records", e)
            }
        }
    }

    companion object {
        fun newInstance(): SleepListFragment {
            return SleepListFragment()
        }
    }
}