package com.example.bitfitpart1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfitpart1.databinding.SleepListBinding

class SleepAdapter(private var sleeps: List<SleepRecord>) : RecyclerView.Adapter<SleepAdapter.SleepViewHolder>() {

    class SleepViewHolder(private val binding: SleepListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sleep: SleepRecord) {
            binding.dateOfSleep.text = sleep.date
            binding.hoursOfSleep.text = sleep.hours.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val binding = SleepListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SleepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        holder.bind(sleeps[position])
    }

    override fun getItemCount() = sleeps.size

    fun updateData(newSleeps: List<SleepRecord>) {
        sleeps = newSleeps
        notifyDataSetChanged()
    }
}
