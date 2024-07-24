package com.example.catdeployer.model

import java.util.UUID

sealed class ListItemUiModel {
    val id: String = UUID.randomUUID().toString()

    data class Title(val title: String) : ListItemUiModel()

    data class Cat(val cat: CatUiModel) : ListItemUiModel()
}
