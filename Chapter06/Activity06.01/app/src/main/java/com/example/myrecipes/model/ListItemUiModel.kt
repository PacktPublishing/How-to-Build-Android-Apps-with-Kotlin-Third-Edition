package com.example.myrecipes.model

import java.util.UUID

sealed class ListItemUiModel {
    val id: String = UUID.randomUUID().toString()

    data class Title(val title: String, val flavor: Flavor) : ListItemUiModel()

    data class Recipe(val recipe: RecipeUiModel) : ListItemUiModel()
}
