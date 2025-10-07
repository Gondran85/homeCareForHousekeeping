package com.jeffersongondran.home_care_housekeeping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jeffersongondran.home_care_housekeeping.data.model.Ingredient
import com.jeffersongondran.home_care_housekeeping.databinding.ItemIngredientSelectionBinding

class IngredientSelectionAdapter(
    private val onIngredientToggle: (Ingredient, Boolean) -> Unit
) : ListAdapter<IngredientSelectionAdapter.IngredientItem, IngredientSelectionAdapter.ViewHolder>(DiffCallback()) {

    data class IngredientItem(
        val ingredient: Ingredient,
        val isSelected: Boolean = false
    )

    class ViewHolder(
        private val binding: ItemIngredientSelectionBinding,
        private val onIngredientToggle: (Ingredient, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IngredientItem) {
            binding.apply {
                tvIngredientName.text = item.ingredient.name
                tvIngredientCategory.text = item.ingredient.category.displayName
                cbIngredient.isChecked = item.isSelected

                cbIngredient.setOnCheckedChangeListener { _, isChecked ->
                    onIngredientToggle(item.ingredient, isChecked)
                }

                root.setOnClickListener {
                    cbIngredient.isChecked = !cbIngredient.isChecked
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onIngredientToggle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<IngredientItem>() {
        override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
            return oldItem.ingredient.id == newItem.ingredient.id
        }

        override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
            return oldItem == newItem
        }
    }
}
