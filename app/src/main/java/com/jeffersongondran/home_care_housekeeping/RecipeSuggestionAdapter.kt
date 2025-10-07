package com.jeffersongondran.home_care_housekeeping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jeffersongondran.home_care_housekeeping.data.model.RecipeSuggestion
import com.jeffersongondran.home_care_housekeeping.databinding.ItemRecipeSuggestionBinding

class RecipeSuggestionAdapter(
    private val onAddToPlan: (RecipeSuggestion) -> Unit
) : ListAdapter<RecipeSuggestion, RecipeSuggestionAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(
        private val binding: ItemRecipeSuggestionBinding,
        private val onAddToPlan: (RecipeSuggestion) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(suggestion: RecipeSuggestion) {
            val recipe = suggestion.recipe

            binding.apply {
                tvRecipeName.text = recipe.name
                tvRecipeDescription.text = recipe.description
                tvCookingTime.text = "${recipe.cookingTimeMinutes} min"
                tvDifficulty.text = recipe.difficulty.displayName
                tvMatchingCount.text = "${suggestion.matchingCount}/${recipe.ingredients.size}"

                // Set difficulty color based on level
                val difficultyColor = when (recipe.difficulty) {
                    com.jeffersongondran.home_care_housekeeping.data.model.Difficulty.EASY ->
                        android.graphics.Color.parseColor("#4CAF50")
                    com.jeffersongondran.home_care_housekeeping.data.model.Difficulty.MEDIUM ->
                        android.graphics.Color.parseColor("#FF9800")
                    com.jeffersongondran.home_care_housekeeping.data.model.Difficulty.HARD ->
                        android.graphics.Color.parseColor("#F44336")
                }
                tvDifficulty.setTextColor(difficultyColor)

                // Clear existing chips and add matching ingredients
                chipGroupIngredients.removeAllViews()
                suggestion.matchingIngredients.forEach { ingredient ->
                    val chip = Chip(binding.root.context).apply {
                        text = ingredient.replace("_", " ").split(" ")
                            .joinToString(" ") { it.capitalize() }
                        isClickable = false
                        setChipBackgroundColorResource(R.color.mint_green)
                        setTextColor(android.graphics.Color.WHITE)
                        textSize = 10f
                    }
                    chipGroupIngredients.addView(chip)
                }

                // Set placeholder image (you can implement actual image loading here)
                ivRecipeImage.setImageResource(android.R.drawable.ic_menu_gallery)

                btnAddToPlan.setOnClickListener {
                    onAddToPlan(suggestion)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeSuggestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onAddToPlan)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<RecipeSuggestion>() {
        override fun areItemsTheSame(oldItem: RecipeSuggestion, newItem: RecipeSuggestion): Boolean {
            return oldItem.recipe.id == newItem.recipe.id
        }

        override fun areContentsTheSame(oldItem: RecipeSuggestion, newItem: RecipeSuggestion): Boolean {
            return oldItem == newItem
        }
    }
}
