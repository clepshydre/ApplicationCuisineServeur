package com.applicuisine.appli_cuisine_server.repositories

import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<RecipeEntity, Int> {

}