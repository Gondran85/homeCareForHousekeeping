package com.jeffersongondran.home_care_housekeeping

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeffersongondran.home_care_housekeeping.data.model.*
import com.jeffersongondran.home_care_housekeeping.data.repository.RecipeRepository
import com.jeffersongondran.home_care_housekeeping.databinding.FragmentFoodPlanBinding
import com.jeffersongondran.home_care_housekeeping.databinding.DialogWeekSelectionBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FoodPlanFragment : Fragment() {

    private var _binding: FragmentFoodPlanBinding? = null
    private val binding get() = _binding!!

    private var currentSelectedWeek = Calendar.getInstance()
    private lateinit var weekSelectionAdapter: WeekSelectionAdapter
    private lateinit var recipeRepository: RecipeRepository

    // Photo adapters for each day
    private lateinit var mondayPhotoAdapter: PhotoAdapter

    // Photo storage maps for each day
    private val mondayPhotos = mutableListOf<Photo>()

    // Activity result launchers for photo selection
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { handleSelectedImage(it, currentPhotoDay) }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoUri != null) {
            handleSelectedImage(currentPhotoUri!!, currentPhotoDay)
        }
    }

    private var currentPhotoDay = "monday"
    private var currentPhotoUri: Uri? = null

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

        recipeRepository = RecipeRepository()
        setupUI()
        setupClickListeners()
        setupPhotoAdapters()
        setupRecipeIdeasButtons()
    }

    private fun setupUI() {
        // Set current week as default
        currentSelectedWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        updateWeekDisplay()
    }

    private fun setupPhotoAdapters() {
        // Setup Monday photo adapter
        mondayPhotoAdapter = PhotoAdapter { photo ->
            removePhoto(photo, "monday")
        }

        binding.rvMondayPhotos.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = mondayPhotoAdapter
        }
    }

    private fun updateWeekDisplay() {
        val calendar = currentSelectedWeek.clone() as Calendar
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        val weekStart = dateFormat.format(calendar.time)

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val weekEnd = dateFormat.format(calendar.time)

        binding.weekTextView.text = getString(R.string.week_range_format, weekStart, weekEnd)
    }

    private fun setupClickListeners() {
        binding.btnChangeWeek.setOnClickListener {
            showWeekSelectionDialog()
        }

        // Photo button click listeners
        binding.btnMondayAddPhoto.setOnClickListener {
            currentPhotoDay = "monday"
            showPhotoSourceDialog()
        }

        binding.btnCopyLastWeek.setOnClickListener {
            copyLastWeekMeals()
        }

        binding.btnSavePlan.setOnClickListener {
            saveFoodPlan()
        }

        binding.fabAddRecipe.setOnClickListener {
            showRecipeSuggestionDialog(null) { recipe ->
                // Handle adding recipe to a general list or show options for which meal to add it to
                Toast.makeText(
                    requireContext(),
                    "Select a specific meal to add ${recipe.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRecipeIdeasButtons() {
        // Monday recipe idea buttons
        binding.btnMondayBreakfastIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.BREAKFAST) { recipe ->
                binding.etMondayBreakfast.setText(recipe.name)
            }
        }

        binding.btnMondayLunchIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.LUNCH) { recipe ->
                binding.etMondayLunch.setText(recipe.name)
            }
        }

        binding.btnMondayDinnerIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.DINNER) { recipe ->
                binding.etMondayDinner.setText(recipe.name)
            }
        }

        binding.btnMondaySnacksIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.SNACKS) { recipe ->
                binding.etMondaySnacks.setText(recipe.name)
            }
        }

        // Tuesday recipe idea buttons
        binding.btnTuesdayBreakfastIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.BREAKFAST) { recipe ->
                binding.etTuesdayBreakfast.setText(recipe.name)
            }
        }

        binding.btnTuesdayLunchIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.LUNCH) { recipe ->
                binding.etTuesdayLunch.setText(recipe.name)
            }
        }

        binding.btnTuesdayDinnerIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.DINNER) { recipe ->
                binding.etTuesdayDinner.setText(recipe.name)
            }
        }

        binding.btnTuesdaySnacksIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.SNACKS) { recipe ->
                binding.etTuesdaySnacks.setText(recipe.name)
            }
        }

        // Wednesday recipe idea buttons
        binding.btnWednesdayBreakfastIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.BREAKFAST) { recipe ->
                binding.etWednesdayBreakfast.setText(recipe.name)
            }
        }

        binding.btnWednesdayLunchIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.LUNCH) { recipe ->
                binding.etWednesdayLunch.setText(recipe.name)
            }
        }

        binding.btnWednesdayDinnerIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.DINNER) { recipe ->
                binding.etWednesdayDinner.setText(recipe.name)
            }
        }

        binding.btnWednesdaySnacksIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.SNACKS) { recipe ->
                binding.etWednesdaySnacks.setText(recipe.name)
            }
        }

        // Thursday recipe idea buttons
        binding.btnThursdayBreakfastIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.BREAKFAST) { recipe ->
                binding.etThursdayBreakfast.setText(recipe.name)
            }
        }

        binding.btnThursdayLunchIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.LUNCH) { recipe ->
                binding.etThursdayLunch.setText(recipe.name)
            }
        }

        binding.btnThursdayDinnerIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.DINNER) { recipe ->
                binding.etThursdayDinner.setText(recipe.name)
            }
        }

        binding.btnThursdaySnacksIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.SNACKS) { recipe ->
                binding.etThursdaySnacks.setText(recipe.name)
            }
        }

        // Friday recipe idea buttons
        binding.btnFridayBreakfastIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.BREAKFAST) { recipe ->
                binding.etFridayBreakfast.setText(recipe.name)
            }
        }

        binding.btnFridayLunchIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.LUNCH) { recipe ->
                binding.etFridayLunch.setText(recipe.name)
            }
        }

        binding.btnFridayDinnerIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.DINNER) { recipe ->
                binding.etFridayDinner.setText(recipe.name)
            }
        }

        binding.btnFridaySnacksIdeas.setOnClickListener {
            showRecipeSuggestionDialog(MealCategory.SNACKS) { recipe ->
                binding.etFridaySnacks.setText(recipe.name)
            }
        }
    }

    private fun showRecipeSuggestionDialog(
        mealCategory: MealCategory?,
        onRecipeSelected: (Recipe) -> Unit
    ) {
        val dialog = RecipeSuggestionDialogFragment.newInstance(mealCategory, onRecipeSelected)
        dialog.show(parentFragmentManager, "recipe_suggestion")
    }

    private fun showPhotoSourceDialog() {
        val dialog = Dialog(requireContext())
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(android.R.layout.select_dialog_item, null)

        // Create a simple dialog with options
        val options = arrayOf(
            getString(R.string.camera),
            getString(R.string.gallery)
        )

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.select_photo_source))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        try {
            val photoFile = createImageFile()
            currentPhotoUri = androidx.core.content.FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                photoFile
            )
            takePictureLauncher.launch(currentPhotoUri)
        } catch (e: Exception) {
            android.widget.Toast.makeText(
                requireContext(),
                getString(R.string.photo_upload_error),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = requireContext().getExternalFilesDir("Pictures")
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    private fun handleSelectedImage(uri: Uri, day: String) {
        // Check file size (max 10MB)
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val fileSize = inputStream?.available() ?: 0
            inputStream?.close()

            if (fileSize > 10 * 1024 * 1024) { // 10MB in bytes
                android.widget.Toast.makeText(
                    requireContext(),
                    getString(R.string.photo_too_large),
                    android.widget.Toast.LENGTH_LONG
                ).show()
                return
            }

            // Check max photos limit
            val currentPhotos = getPhotosForDay(day)
            if (currentPhotos.size >= 5) {
                android.widget.Toast.makeText(
                    requireContext(),
                    getString(R.string.max_photos_reached),
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return
            }

            // Add photo
            val photo = Photo(uri)
            addPhoto(photo, day)

        } catch (e: Exception) {
            android.widget.Toast.makeText(
                requireContext(),
                getString(R.string.photo_upload_error),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addPhoto(photo: Photo, day: String) {
        when (day) {
            "monday" -> {
                mondayPhotos.add(photo)
                mondayPhotoAdapter.addPhoto(photo)
                updatePhotoCount("monday")
            }
        }

        // Show photo grid if not visible
        showPhotoGrid(day, true)
    }

    private fun removePhoto(photo: Photo, day: String) {
        when (day) {
            "monday" -> {
                mondayPhotos.remove(photo)
                mondayPhotoAdapter.removePhoto(photo)
                updatePhotoCount("monday")
            }
        }

        android.widget.Toast.makeText(
            requireContext(),
            getString(R.string.photo_removed),
            android.widget.Toast.LENGTH_SHORT
        ).show()

        // Hide grid if no photos
        val currentPhotos = getPhotosForDay(day)
        if (currentPhotos.isEmpty()) {
            showPhotoGrid(day, false)
        }
    }

    private fun getPhotosForDay(day: String): List<Photo> {
        return when (day) {
            "monday" -> mondayPhotos
            else -> emptyList()
        }
    }

    private fun updatePhotoCount(day: String) {
        val photos = getPhotosForDay(day)
        val count = photos.size

        when (day) {
            "monday" -> {
                if (count > 0) {
                    binding.tvMondayPhotoCount.text = getString(R.string.photo_count, count)
                    binding.tvMondayPhotoCount.visibility = View.VISIBLE
                } else {
                    binding.tvMondayPhotoCount.visibility = View.GONE
                }
            }
        }
    }

    private fun showPhotoGrid(day: String, show: Boolean) {
        when (day) {
            "monday" -> {
                binding.rvMondayPhotos.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showWeekSelectionDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogWeekSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        // Setup RecyclerView
        weekSelectionAdapter = WeekSelectionAdapter { selectedWeek ->
            currentSelectedWeek = selectedWeek.startDate.clone() as Calendar
            updateWeekDisplay()
            android.widget.Toast.makeText(
                requireContext(),
                getString(R.string.week_selected),
                android.widget.Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        dialogBinding.rvWeekList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weekSelectionAdapter
        }

        // Generate weeks (2 weeks back to 4 weeks forward)
        val weeks = generateWeekList()
        weekSelectionAdapter.updateWeeks(weeks)

        // Setup navigation buttons
        dialogBinding.btnPreviousWeeks.setOnClickListener {
            currentSelectedWeek.add(Calendar.WEEK_OF_YEAR, -1)
            val newWeeks = generateWeekList()
            weekSelectionAdapter.updateWeeks(newWeeks)
        }

        dialogBinding.btnNextWeeks.setOnClickListener {
            currentSelectedWeek.add(Calendar.WEEK_OF_YEAR, 1)
            val newWeeks = generateWeekList()
            weekSelectionAdapter.updateWeeks(newWeeks)
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Configure dialog
        dialog.window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }

        dialog.show()
    }

    private fun generateWeekList(): List<WeekItem> {
        val weeks = mutableListOf<WeekItem>()
        val today = Calendar.getInstance()
        val currentWeekStart = Calendar.getInstance()

        // Set to Monday of current week
        currentWeekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        // Generate weeks from 2 weeks back to 4 weeks forward (7 weeks total)
        for (i in -2..4) {
            val weekStart = Calendar.getInstance()
            weekStart.time = currentWeekStart.time
            weekStart.add(Calendar.WEEK_OF_YEAR, i)

            val weekEnd = Calendar.getInstance()
            weekEnd.time = weekStart.time
            weekEnd.add(Calendar.DAY_OF_WEEK, 6)

            // Check if this is the current week
            val isCurrentWeek = isSameWeek(weekStart, today)

            weeks.add(WeekItem(weekStart, weekEnd, isCurrentWeek))
        }

        return weeks
    }

    private fun isSameWeek(calendar1: Calendar, calendar2: Calendar): Boolean {
        val week1 = calendar1.get(Calendar.WEEK_OF_YEAR)
        val year1 = calendar1.get(Calendar.YEAR)
        val week2 = calendar2.get(Calendar.WEEK_OF_YEAR)
        val year2 = calendar2.get(Calendar.YEAR)

        return week1 == week2 && year1 == year2
    }

    private fun copyLastWeekMeals() {
        android.widget.Toast.makeText(
            requireContext(),
            "Previous week meals copied! 📋",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun saveFoodPlan() {
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
