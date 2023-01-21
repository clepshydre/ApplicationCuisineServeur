package com.applicuisine.appli_cuisine_server.dto

import com.applicuisine.appli_cuisine_server.entities.ComposeEntity
import com.applicuisine.appli_cuisine_server.entities.IngredientEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import com.applicuisine.appli_cuisine_server.entities.UnitEntity

class ComposeDTO (

    var quantity: Double? = 0.0,

    var unitName: String? = "",

    var ingredientName:String? ="",

    )

fun ComposeEntity.toDTO() = ComposeDTO(
    ingredientName = this.ingredientByIdIngredient?.name,
    quantity = this.quantity,
    unitName = this.unitByIdUnit?.name
)

fun ComposeDTO.toComposeEntity(recipeEntity: RecipeEntity, unitEntity: UnitEntity, ingredientEntity: IngredientEntity) = ComposeEntity(
    idCompose = null,
    quantity = quantity,
    recipeByIdRecipe = recipeEntity,
    unitByIdUnit = unitEntity,
    ingredientByIdIngredient = ingredientEntity
)