package com.jeffersongondran.home_care_housekeeping.data.model

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val cookingTimeMinutes: Int,
    val difficulty: Difficulty,
    val imageUrl: String? = null,
    val instructions: List<String> = emptyList(),
    val category: MealCategory = MealCategory.LUNCH
)

data class Ingredient(
    val id: String,
    val name: String,
    val category: IngredientCategory
)

enum class Difficulty(val displayName: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}

enum class MealCategory(val displayName: String) {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACKS("Snacks")
}

enum class IngredientCategory(val displayName: String) {
    PROTEINS("Proteins"),
    VEGETABLES("Vegetables"),
    GRAINS("Grains & Cereals"),
    DAIRY("Dairy"),
    SPICES("Spices & Herbs"),
    FRUITS("Fruits"),
    PANTRY("Pantry Items"),
    OILS("Oils & Fats")
}

data class RecipeSuggestion(
    val recipe: Recipe,
    val matchingIngredients: List<String>,
    val matchingCount: Int,
    val matchingPercentage: Float
)

data class MealPlan(
    val id: String,
    val dayOfWeek: String,
    val mealType: MealCategory,
    val recipe: Recipe? = null,
    val customMealName: String? = null,
    val photos: List<String> = emptyList()
)
