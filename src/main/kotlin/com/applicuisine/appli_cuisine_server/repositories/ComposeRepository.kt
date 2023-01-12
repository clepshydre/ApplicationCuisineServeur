package com.applicuisine.appli_cuisine_server.repositories;

import com.applicuisine.appli_cuisine_server.entities.ComposeEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ComposeRepository : JpaRepository<ComposeEntity, Int> {

    fun findComposeEntityByRecipeByIdRecipe(recipeEntity: RecipeEntity): Optional<List<ComposeEntity>>
}