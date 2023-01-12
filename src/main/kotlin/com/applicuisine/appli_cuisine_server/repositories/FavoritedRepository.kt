package com.applicuisine.appli_cuisine_server.repositories;

import com.applicuisine.appli_cuisine_server.entities.FavoritedEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import com.applicuisine.appli_cuisine_server.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoritedRepository : JpaRepository<FavoritedEntity, Int> {

    fun findAllByUsersByIdUser(userEntity: UserEntity): Optional<List<FavoritedEntity>>
    fun findByUsersByIdUserAndRecipeByIdRecipe(userEntity: UserEntity, recipeEntity: RecipeEntity): Optional<FavoritedEntity>
}