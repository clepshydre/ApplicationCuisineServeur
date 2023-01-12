package com.applicuisine.appli_cuisine_server.repositories;

import com.applicuisine.appli_cuisine_server.entities.IngredientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<IngredientEntity, Int> {
}