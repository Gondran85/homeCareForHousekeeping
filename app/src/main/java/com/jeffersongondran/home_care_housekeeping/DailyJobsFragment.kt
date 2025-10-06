package com.jeffersongondran.home_care_housekeeping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.jeffersongondran.home_care_housekeeping.databinding.FragmentDailyJobsBinding
import java.text.SimpleDateFormat
import java.util.*

class DailyJobsFragment : Fragment() {

    private var _binding: FragmentDailyJobsBinding? = null
    private val binding get() = _binding!!

    private lateinit var allCheckBoxes: List<CheckBox>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Set current date
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())
        binding.dateTextView.text = dateFormat.format(Date())

        // Collect all checkboxes
        allCheckBoxes = listOf(
            // Morning tasks
            binding.cbPrepareBreakfast,
            binding.cbGetKidsReady,
            binding.cbSchoolDropoff,
            binding.cbTidyKitchen,
            binding.cbMakeBeds,
            // Daytime tasks
            binding.cbCleanLivingRoom,
            binding.cbMopVacuum,
            binding.cbLaundry,
            binding.cbCleanBathrooms,
            binding.cbWaterPlants,
            binding.cbOrganizeToys,
            binding.cbPrepareLunchDinner,
            // Afternoon tasks
            binding.cbSchoolPickup,
            binding.cbServeSnacks,
            binding.cbKidsPlaytime,
            binding.cbReadingTime,
            // Evening tasks
            binding.cbDinnerPrep,
            binding.cbBathBedtime,
            binding.cbPrepareUniforms,
            binding.cbQuickTidyup
        )
    }

    private fun setupClickListeners() {
        binding.btnMarkAllDone.setOnClickListener {
            markAllTasksComplete()
        }

        binding.fabAddTask.setOnClickListener {
            // TODO: Implement add new task functionality
            // For now, show a toast
            android.widget.Toast.makeText(
                requireContext(),
                "Add New Task feature coming soon!",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun markAllTasksComplete() {
        allCheckBoxes.forEach { checkBox ->
            checkBox.isChecked = true
        }

        android.widget.Toast.makeText(
            requireContext(),
            "All tasks marked as complete! 🎉",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
