package com.jeffersongondran.home_care_housekeeping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jeffersongondran.home_care_housekeeping.databinding.FragmentFoodPlanBinding
import java.text.SimpleDateFormat
import java.util.*

class FoodPlanFragment : Fragment() {

    private var _binding: FragmentFoodPlanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Set current week
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        val weekStart = dateFormat.format(calendar.time)

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val weekEnd = dateFormat.format(calendar.time)

        binding.weekTextView.text = getString(R.string.week_range_format, weekStart, weekEnd)
    }

    private fun setupClickListeners() {
        binding.btnCopyLastWeek.setOnClickListener {
            copyLastWeekMeals()
        }

        binding.btnSavePlan.setOnClickListener {
            saveFoodPlan()
        }

        binding.fabAddRecipe.setOnClickListener {
            // TODO: Implement add recipe functionality
            android.widget.Toast.makeText(
                requireContext(),
                "Add Recipe feature coming soon!",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun copyLastWeekMeals() {
        // TODO: Implement copy from previous week
        android.widget.Toast.makeText(
            requireContext(),
            "Previous week meals copied! 📋",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun saveFoodPlan() {
        // TODO: Implement actual saving logic with SharedPreferences or database
        // For now, just show confirmation
        android.widget.Toast.makeText(
            requireContext(),
            "Food plan saved successfully! ✅",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
