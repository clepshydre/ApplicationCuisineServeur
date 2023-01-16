package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.entities.IngredientEntity
import com.applicuisine.appli_cuisine_server.repositories.IngredientRepository
import org.springframework.stereotype.Service

@Service
class IngredientService(val ingredientRepository: IngredientRepository) {

    fun find(name: String): IngredientEntity{
        val optionalIngredient = ingredientRepository.findByName(name)
        if(optionalIngredient.isPresent){
            return optionalIngredient.get()
        }else{
            throw Exception("Ingredient couldn't be find with this name: $name")
        }
    }
}