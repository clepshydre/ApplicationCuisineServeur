package com.applicuisine.appli_cuisine_server.dto

import com.applicuisine.appli_cuisine_server.entities.RecipeEntity

class RecipeRVDTO (
    var id: Int? = null,

    var name: String? = null,

    var image: String? = null,

    var like:Boolean? = null
)

fun RecipeEntity.toRVDTO() = RecipeRVDTO(
    id = id,
    name = name,
    image = image
)