package com.example.bitfitpart1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.bitfitpart1.databinding.FragmentSleepAverageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SleepAverageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SleepAverageFragment : Fragment() {

    private var _binding: FragmentSleepAverageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepAverageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAndDisplayData()

        binding.btnDeleteAll.setOnClickListener {
            deleteAllRecords()
        }
    }
    private fun deleteAllRecords() {
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "bitfit-database"
        ).build()

        lifecycleScope.launch(Dispatchers.IO) {
            db.sleepDao().deleteAll()
        }
    }
    private fun fetchAndDisplayData() {
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "bitfit-database"
        ).build()

        // Using LiveData to observe data changes
        val dao = db.sleepDao()
        dao.getAverageSleepHours().observe(viewLifecycleOwner) { average ->
            val roundedAverage = String.format("%.1f", average ?: 0.0)
            binding.tvAverageSleep.text = "Average Sleep: $roundedAverage hours"
        }

        dao.getSleepRecordCount().observe(viewLifecycleOwner) { count ->
            binding.tvDaysRecorded.text = "Days Recorded: $count"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SleepAverageFragment()
    }
}
