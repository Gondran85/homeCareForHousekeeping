package com.jeffersongondran.home_care_housekeeping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeffersongondran.home_care_housekeeping.databinding.ItemWeekSelectionBinding
import java.text.SimpleDateFormat
import java.util.*

data class WeekItem(
    val startDate: Calendar,
    val endDate: Calendar,
    val isCurrentWeek: Boolean = false
)

class WeekSelectionAdapter(
    private val onWeekSelected: (WeekItem) -> Unit
) : RecyclerView.Adapter<WeekSelectionAdapter.WeekViewHolder>() {

    private var weeks = mutableListOf<WeekItem>()
    private var selectedPosition = -1

    fun updateWeeks(newWeeks: List<WeekItem>) {
        val oldSize = weeks.size
        weeks.clear()
        weeks.addAll(newWeeks)

        // Find current week position
        selectedPosition = weeks.indexOfFirst { it.isCurrentWeek }

        if (oldSize == newWeeks.size) {
            notifyItemRangeChanged(0, weeks.size)
        } else {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val binding = ItemWeekSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeekViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(weeks[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = weeks.size

    inner class WeekViewHolder(
        private val binding: ItemWeekSelectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weekItem: WeekItem, isSelected: Boolean) {
            val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
            val weekStart = dateFormat.format(weekItem.startDate.time)
            val weekEnd = dateFormat.format(weekItem.endDate.time)

            // Use string resource with placeholders
            binding.tvWeekRange.text = itemView.context.getString(
                R.string.week_range_format, weekStart, weekEnd
            )

            // Show "This Week" label if it's the current week
            binding.tvThisWeekLabel.visibility = if (weekItem.isCurrentWeek) {
                View.VISIBLE
            } else {
                View.GONE
            }

            // Highlight selected week
            if (isSelected) {
                binding.root.strokeWidth = 3
                binding.root.strokeColor = itemView.context.getColor(R.color.primary)
            } else {
                binding.root.strokeWidth = 0
            }

            binding.root.setOnClickListener {
                val oldPosition = selectedPosition
                selectedPosition = bindingAdapterPosition
                if (oldPosition != -1) notifyItemChanged(oldPosition)
                if (selectedPosition != -1) notifyItemChanged(selectedPosition)
                onWeekSelected(weekItem)
            }
        }
    }
}
