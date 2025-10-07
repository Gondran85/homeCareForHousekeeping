package com.jeffersongondran.home_care_housekeeping.data.repository

import com.jeffersongondran.home_care_housekeeping.data.model.*

class RecipeRepository {

    private val ingredients = listOf(
        // Proteins
        Ingredient("chicken_breast", "Chicken Breast", IngredientCategory.PROTEINS),
        Ingredient("eggs", "Eggs", IngredientCategory.PROTEINS),
        Ingredient("ground_beef", "Ground Beef", IngredientCategory.PROTEINS),
        Ingredient("salmon", "Salmon", IngredientCategory.PROTEINS),
        Ingredient("tofu", "Tofu", IngredientCategory.PROTEINS),
        Ingredient("bacon", "Bacon", IngredientCategory.PROTEINS),
        Ingredient("shrimp", "Shrimp", IngredientCategory.PROTEINS),

        // Vegetables
        Ingredient("tomatoes", "Tomatoes", IngredientCategory.VEGETABLES),
        Ingredient("onions", "Onions", IngredientCategory.VEGETABLES),
        Ingredient("garlic", "Garlic", IngredientCategory.VEGETABLES),
        Ingredient("bell_peppers", "Bell Peppers", IngredientCategory.VEGETABLES),
        Ingredient("carrots", "Carrots", IngredientCategory.VEGETABLES),
        Ingredient("broccoli", "Broccoli", IngredientCategory.VEGETABLES),
        Ingredient("spinach", "Spinach", IngredientCategory.VEGETABLES),
        Ingredient("mushrooms", "Mushrooms", IngredientCategory.VEGETABLES),
        Ingredient("lettuce", "Lettuce", IngredientCategory.VEGETABLES),
        Ingredient("cucumber", "Cucumber", IngredientCategory.VEGETABLES),

        // Grains & Cereals
        Ingredient("rice", "Rice", IngredientCategory.GRAINS),
        Ingredient("pasta", "Pasta", IngredientCategory.GRAINS),
        Ingredient("bread", "Bread", IngredientCategory.GRAINS),
        Ingredient("oats", "Oats", IngredientCategory.GRAINS),
        Ingredient("quinoa", "Quinoa", IngredientCategory.GRAINS),
        Ingredient("flour", "Flour", IngredientCategory.GRAINS),

        // Dairy
        Ingredient("milk", "Milk", IngredientCategory.DAIRY),
        Ingredient("cheese", "Cheese", IngredientCategory.DAIRY),
        Ingredient("butter", "Butter", IngredientCategory.DAIRY),
        Ingredient("yogurt", "Yogurt", IngredientCategory.DAIRY),
        Ingredient("cream", "Heavy Cream", IngredientCategory.DAIRY),

        // Spices & Herbs
        Ingredient("salt", "Salt", IngredientCategory.SPICES),
        Ingredient("pepper", "Black Pepper", IngredientCategory.SPICES),
        Ingredient("basil", "Basil", IngredientCategory.SPICES),
        Ingredient("oregano", "Oregano", IngredientCategory.SPICES),
        Ingredient("paprika", "Paprika", IngredientCategory.SPICES),
        Ingredient("cumin", "Cumin", IngredientCategory.SPICES),
        Ingredient("thyme", "Thyme", IngredientCategory.SPICES),

        // Fruits
        Ingredient("bananas", "Bananas", IngredientCategory.FRUITS),
        Ingredient("apples", "Apples", IngredientCategory.FRUITS),
        Ingredient("berries", "Mixed Berries", IngredientCategory.FRUITS),
        Ingredient("lemon", "Lemon", IngredientCategory.FRUITS),

        // Pantry Items
        Ingredient("olive_oil", "Olive Oil", IngredientCategory.OILS),
        Ingredient("vegetable_oil", "Vegetable Oil", IngredientCategory.OILS),
        Ingredient("soy_sauce", "Soy Sauce", IngredientCategory.PANTRY),
        Ingredient("honey", "Honey", IngredientCategory.PANTRY),
        Ingredient("baking_powder", "Baking Powder", IngredientCategory.PANTRY)
    )

    private val recipes = listOf(
        Recipe(
            id = "chicken_fried_rice",
            name = "Chicken Fried Rice",
            description = "Delicious fried rice with chicken and vegetables",
            ingredients = listOf("chicken_breast", "rice", "eggs", "carrots", "onions", "garlic", "soy_sauce", "vegetable_oil"),
            cookingTimeMinutes = 25,
            difficulty = Difficulty.MEDIUM,
            category = MealCategory.LUNCH,
            instructions = listOf(
                "Cook rice and let it cool",
                "Cut chicken into small pieces and cook",
                "Scramble eggs and set aside",
                "Stir-fry vegetables",
                "Combine everything with soy sauce"
            )
        ),
        Recipe(
            id = "scrambled_eggs_toast",
            name = "Scrambled Eggs with Toast",
            description = "Classic breakfast with fluffy scrambled eggs",
            ingredients = listOf("eggs", "bread", "butter", "milk", "salt", "pepper"),
            cookingTimeMinutes = 10,
            difficulty = Difficulty.EASY,
            category = MealCategory.BREAKFAST,
            instructions = listOf(
                "Beat eggs with milk, salt, and pepper",
                "Heat butter in pan",
                "Cook eggs on low heat, stirring gently",
                "Toast bread and serve together"
            )
        ),
        Recipe(
            id = "pasta_tomato_sauce",
            name = "Pasta with Tomato Sauce",
            description = "Simple and delicious pasta with homemade tomato sauce",
            ingredients = listOf("pasta", "tomatoes", "garlic", "onions", "basil", "olive_oil", "salt", "pepper"),
            cookingTimeMinutes = 20,
            difficulty = Difficulty.EASY,
            category = MealCategory.DINNER,
            instructions = listOf(
                "Boil pasta according to package instructions",
                "Sauté garlic and onions in olive oil",
                "Add tomatoes and seasonings",
                "Simmer sauce for 10 minutes",
                "Toss pasta with sauce"
            )
        ),
        Recipe(
            id = "grilled_salmon",
            name = "Grilled Salmon with Vegetables",
            description = "Healthy grilled salmon with roasted vegetables",
            ingredients = listOf("salmon", "broccoli", "carrots", "olive_oil", "lemon", "garlic", "salt", "pepper"),
            cookingTimeMinutes = 30,
            difficulty = Difficulty.MEDIUM,
            category = MealCategory.DINNER,
            instructions = listOf(
                "Season salmon with salt, pepper, and lemon",
                "Cut vegetables into pieces",
                "Roast vegetables with olive oil and garlic",
                "Grill salmon for 4-5 minutes per side",
                "Serve together"
            )
        ),
        Recipe(
            id = "chicken_caesar_salad",
            name = "Chicken Caesar Salad",
            description = "Fresh caesar salad with grilled chicken",
            ingredients = listOf("chicken_breast", "lettuce", "cheese", "bread", "garlic", "olive_oil", "lemon"),
            cookingTimeMinutes = 15,
            difficulty = Difficulty.EASY,
            category = MealCategory.LUNCH,
            instructions = listOf(
                "Grill chicken breast and slice",
                "Make croutons from bread",
                "Prepare caesar dressing",
                "Toss lettuce with dressing",
                "Top with chicken, cheese, and croutons"
            )
        ),
        Recipe(
            id = "vegetable_stir_fry",
            name = "Vegetable Stir Fry",
            description = "Quick and healthy mixed vegetable stir fry",
            ingredients = listOf("broccoli", "bell_peppers", "carrots", "mushrooms", "garlic", "soy_sauce", "vegetable_oil"),
            cookingTimeMinutes = 15,
            difficulty = Difficulty.EASY,
            category = MealCategory.LUNCH,
            instructions = listOf(
                "Cut all vegetables into bite-sized pieces",
                "Heat oil in wok or large pan",
                "Stir-fry vegetables starting with harder ones",
                "Add garlic and soy sauce",
                "Serve immediately"
            )
        ),
        Recipe(
            id = "oatmeal_berries",
            name = "Oatmeal with Berries",
            description = "Nutritious breakfast oatmeal topped with fresh berries",
            ingredients = listOf("oats", "milk", "berries", "honey", "salt"),
            cookingTimeMinutes = 8,
            difficulty = Difficulty.EASY,
            category = MealCategory.BREAKFAST,
            instructions = listOf(
                "Bring milk to a boil",
                "Add oats and a pinch of salt",
                "Cook for 5 minutes, stirring occasionally",
                "Top with berries and honey",
                "Serve warm"
            )
        ),
        Recipe(
            id = "beef_tacos",
            name = "Ground Beef Tacos",
            description = "Flavorful ground beef tacos with fresh toppings",
            ingredients = listOf("ground_beef", "onions", "garlic", "tomatoes", "lettuce", "cheese", "cumin", "paprika"),
            cookingTimeMinutes = 20,
            difficulty = Difficulty.MEDIUM,
            category = MealCategory.DINNER,
            instructions = listOf(
                "Brown ground beef in pan",
                "Add onions, garlic, and spices",
                "Cook until vegetables are soft",
                "Prepare fresh toppings",
                "Assemble tacos and serve"
            )
        ),
        Recipe(
            id = "greek_yogurt_parfait",
            name = "Greek Yogurt Parfait",
            description = "Healthy layered parfait with yogurt and fruit",
            ingredients = listOf("yogurt", "berries", "honey", "oats"),
            cookingTimeMinutes = 5,
            difficulty = Difficulty.EASY,
            category = MealCategory.SNACKS,
            instructions = listOf(
                "Layer yogurt in a glass or bowl",
                "Add berries and drizzle with honey",
                "Sprinkle oats on top",
                "Repeat layers as desired",
                "Serve immediately"
            )
        ),
        Recipe(
            id = "mushroom_risotto",
            name = "Mushroom Risotto",
            description = "Creamy mushroom risotto with herbs",
            ingredients = listOf("rice", "mushrooms", "onions", "garlic", "cream", "cheese", "olive_oil", "thyme"),
            cookingTimeMinutes = 35,
            difficulty = Difficulty.HARD,
            category = MealCategory.DINNER,
            instructions = listOf(
                "Sauté mushrooms and set aside",
                "Cook onions and garlic in olive oil",
                "Add rice and toast lightly",
                "Add liquid gradually, stirring constantly",
                "Stir in mushrooms, cream, and cheese"
            )
        )
    )

    fun getAllIngredients(): List<Ingredient> = ingredients

    fun getIngredientsByCategory(): Map<IngredientCategory, List<Ingredient>> =
        ingredients.groupBy { it.category }

    fun getAllRecipes(): List<Recipe> = recipes

    fun getRecipesByCategory(category: MealCategory): List<Recipe> =
        recipes.filter { it.category == category }

    fun findRecipeSuggestions(
        selectedIngredients: List<String>,
        mealCategory: MealCategory? = null
    ): List<RecipeSuggestion> {
        val recipesToSearch = if (mealCategory != null) {
            getRecipesByCategory(mealCategory)
        } else {
            recipes
        }

        return recipesToSearch.mapNotNull { recipe ->
            val matchingIngredients = recipe.ingredients.intersect(selectedIngredients.toSet())
            if (matchingIngredients.isNotEmpty()) {
                RecipeSuggestion(
                    recipe = recipe,
                    matchingIngredients = matchingIngredients.toList(),
                    matchingCount = matchingIngredients.size,
                    matchingPercentage = (matchingIngredients.size.toFloat() / recipe.ingredients.size) * 100
                )
            } else null
        }.sortedWith(
            compareByDescending<RecipeSuggestion> { it.matchingCount }
                .thenByDescending { it.matchingPercentage }
        )
    }

    fun getRecipeById(id: String): Recipe? = recipes.find { it.id == id }
}
