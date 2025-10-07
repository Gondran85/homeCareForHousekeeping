package com.jeffersongondran.home_care_housekeeping

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeffersongondran.home_care_housekeeping.data.model.*
import com.jeffersongondran.home_care_housekeeping.data.repository.RecipeRepository
import com.jeffersongondran.home_care_housekeeping.databinding.DialogIngredientSelectionBinding
import com.jeffersongondran.home_care_housekeeping.databinding.DialogRecipeSuggestionsBinding

class RecipeSuggestionDialogFragment : DialogFragment() {

    private var _ingredientBinding: DialogIngredientSelectionBinding? = null
    private var _recipeBinding: DialogRecipeSuggestionsBinding? = null
    private val ingredientBinding get() = _ingredientBinding!!
    private val recipeBinding get() = _recipeBinding!!

    private lateinit var recipeRepository: RecipeRepository
    private lateinit var ingredientAdapter: IngredientSelectionAdapter
    private lateinit var recipeAdapter: RecipeSuggestionAdapter

    private val selectedIngredients = mutableSetOf<String>()
    private var allIngredients = listOf<Ingredient>()
    private var filteredIngredients = listOf<Ingredient>()
    private var currentMealCategory: MealCategory? = null
    private var onRecipeSelected: ((Recipe) -> Unit)? = null

    private var currentStep = Step.INGREDIENT_SELECTION

    enum class Step {
        INGREDIENT_SELECTION,
        RECIPE_SUGGESTIONS
    }

    companion object {
        fun newInstance(
            mealCategory: MealCategory? = null,
            onRecipeSelected: (Recipe) -> Unit
        ): RecipeSuggestionDialogFragment {
            return RecipeSuggestionDialogFragment().apply {
                this.currentMealCategory = mealCategory
                this.onRecipeSelected = onRecipeSelected
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipeRepository = RecipeRepository()

        return when (currentStep) {
            Step.INGREDIENT_SELECTION -> {
                _ingredientBinding = DialogIngredientSelectionBinding.inflate(inflater, container, false)
                setupIngredientSelection()
                ingredientBinding.root
            }
            Step.RECIPE_SUGGESTIONS -> {
                _recipeBinding = DialogRecipeSuggestionsBinding.inflate(inflater, container, false)
                setupRecipeSuggestions()
                recipeBinding.root
            }
        }
    }

    private fun setupIngredientSelection() {
        allIngredients = recipeRepository.getAllIngredients()
        filteredIngredients = allIngredients

        ingredientAdapter = IngredientSelectionAdapter { ingredient, isSelected ->
            if (isSelected) {
                selectedIngredients.add(ingredient.id)
            } else {
                selectedIngredients.remove(ingredient.id)
            }
            updateSelectedCount()
        }

        ingredientBinding.apply {
            rvIngredients.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ingredientAdapter
            }

            updateIngredientList()
            updateSelectedCount()

            // Search functionality
            etSearchIngredients.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    filterIngredients(s.toString())
                }
            })

            btnClearAll.setOnClickListener {
                selectedIngredients.clear()
                updateIngredientList()
                updateSelectedCount()
            }

            btnCancel.setOnClickListener {
                dismiss()
            }

            btnGetSuggestions.setOnClickListener {
                if (selectedIngredients.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please select at least one ingredient",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showRecipeSuggestions()
                }
            }
        }
    }

    private fun setupRecipeSuggestions() {
        recipeAdapter = RecipeSuggestionAdapter { suggestion ->
            onRecipeSelected?.invoke(suggestion.recipe)
            Toast.makeText(
                requireContext(),
                "${suggestion.recipe.name} added to meal plan!",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }

        recipeBinding.apply {
            rvRecipeSuggestions.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recipeAdapter
            }

            btnClose.setOnClickListener {
                dismiss()
            }

            // Filter chips
            chipAllMeals.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherChips(chipAllMeals.id)
                    filterRecipes(null)
                }
            }

            chipBreakfast.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherChips(chipBreakfast.id)
                    filterRecipes(MealCategory.BREAKFAST)
                }
            }

            chipLunch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherChips(chipLunch.id)
                    filterRecipes(MealCategory.LUNCH)
                }
            }

            chipDinner.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherChips(chipDinner.id)
                    filterRecipes(MealCategory.DINNER)
                }
            }

            chipSnacks.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherChips(chipSnacks.id)
                    filterRecipes(MealCategory.SNACKS)
                }
            }

            loadRecipeSuggestions()
        }
    }

    private fun filterIngredients(query: String) {
        filteredIngredients = if (query.isBlank()) {
            allIngredients
        } else {
            allIngredients.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.category.displayName.contains(query, ignoreCase = true)
            }
        }
        updateIngredientList()
    }

    private fun updateIngredientList() {
        val items = filteredIngredients.map { ingredient ->
            IngredientSelectionAdapter.IngredientItem(
                ingredient = ingredient,
                isSelected = selectedIngredients.contains(ingredient.id)
            )
        }
        ingredientAdapter.submitList(items)
    }

    private fun updateSelectedCount() {
        ingredientBinding.tvSelectedCount.text =
            "${selectedIngredients.size} ingredients selected"
    }

    private fun showRecipeSuggestions() {
        currentStep = Step.RECIPE_SUGGESTIONS

        // Recreate the view with recipe suggestions
        val transaction = parentFragmentManager.beginTransaction()
        transaction.remove(this)
        transaction.commit()

        val newDialog = RecipeSuggestionDialogFragment().apply {
            this.currentStep = Step.RECIPE_SUGGESTIONS
            this.selectedIngredients.addAll(this@RecipeSuggestionDialogFragment.selectedIngredients)
            this.currentMealCategory = this@RecipeSuggestionDialogFragment.currentMealCategory
            this.onRecipeSelected = this@RecipeSuggestionDialogFragment.onRecipeSelected
            this.recipeRepository = this@RecipeSuggestionDialogFragment.recipeRepository
        }

        newDialog.show(parentFragmentManager, "recipe_suggestions")
    }

    private fun loadRecipeSuggestions() {
        val suggestions = recipeRepository.findRecipeSuggestions(
            selectedIngredients.toList(),
            currentMealCategory
        )

        if (suggestions.isEmpty()) {
            recipeBinding.layoutEmptyState.visibility = View.VISIBLE
            recipeBinding.rvRecipeSuggestions.visibility = View.GONE
        } else {
            recipeBinding.layoutEmptyState.visibility = View.GONE
            recipeBinding.rvRecipeSuggestions.visibility = View.VISIBLE
            recipeAdapter.submitList(suggestions)
        }

        val selectedCount = selectedIngredients.size
        recipeBinding.tvSuggestionsSubtitle.text =
            "Based on $selectedCount selected ingredients • ${suggestions.size} recipes found"
    }

    private fun filterRecipes(category: MealCategory?) {
        val suggestions = recipeRepository.findRecipeSuggestions(
            selectedIngredients.toList(),
            category
        )
        recipeAdapter.submitList(suggestions)

        if (suggestions.isEmpty()) {
            recipeBinding.layoutEmptyState.visibility = View.VISIBLE
            recipeBinding.rvRecipeSuggestions.visibility = View.GONE
        } else {
            recipeBinding.layoutEmptyState.visibility = View.GONE
            recipeBinding.rvRecipeSuggestions.visibility = View.VISIBLE
        }
    }

    private fun uncheckOtherChips(checkedChipId: Int) {
        recipeBinding.apply {
            val chips = listOf(chipAllMeals, chipBreakfast, chipLunch, chipDinner, chipSnacks)
            chips.forEach { chip ->
                if (chip.id != checkedChipId) {
                    chip.isChecked = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ingredientBinding = null
        _recipeBinding = null
    }
}
