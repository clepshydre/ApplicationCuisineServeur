package com.applicuisine.appli_cuisine_server.dto

import com.applicuisine.appli_cuisine_server.entities.RecipeEntity

class RecipeDisplayDTO (

    var id: Int? = null,

    var name: String? = null,

    var preparationTime: Int? = null,

    var cookingTime: Int? = null,

    var waitingTime: Int? = null,

    var totalTime :Int? = null,

    var difficulty: Int? = null,

    var cost: Int? = null,

    var image: String? = null,

    var like:Boolean? = null,

    var listIngredientDTO: List<IngredientDTO> = emptyList(),

    var listInstructionDTO: List<InstructionDTO> = emptyList()
)

fun RecipeEntity.toDisplayDTO() = RecipeDisplayDTO(
    id = id,
    name = name,
    preparationTime = preparationTime,
    cookingTime = cookingTime,
    waitingTime = waitingTime,
    difficulty = difficulty,
    totalTime = preparationTime!!+cookingTime!!+waitingTime!!,
    cost = cost,
    image = image
)