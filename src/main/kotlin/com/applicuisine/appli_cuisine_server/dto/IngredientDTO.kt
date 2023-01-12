package com.applicuisine.appli_cuisine_server.dto

import com.applicuisine.appli_cuisine_server.entities.ComposeEntity
import com.applicuisine.appli_cuisine_server.entities.IngredientEntity

class IngredientDTO (

    val quantity: Double? = 0.0,

    val unit: String? = "",

    val name:String? ="",

)

fun ComposeEntity.toDTO() = IngredientDTO(
    name = this.ingredientByIdIngredient?.name,
    quantity = this.quantity,
    unit = this.unitByIdUnit?.name
)