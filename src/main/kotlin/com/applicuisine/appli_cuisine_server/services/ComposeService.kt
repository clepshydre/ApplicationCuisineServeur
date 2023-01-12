package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.dto.*
import com.applicuisine.appli_cuisine_server.entities.ComposeEntity
import com.applicuisine.appli_cuisine_server.entities.InstructionEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import com.applicuisine.appli_cuisine_server.entities.UserEntity
import com.applicuisine.appli_cuisine_server.repositories.ComposeRepository
import org.springframework.stereotype.Service

@Service
class ComposeService(val composeRepository: ComposeRepository, val instructionService: InstructionService, val favoriteService: FavoriteService) {


    fun getRecipeToDisplay(recipe: RecipeEntity, userEntity: UserEntity):RecipeDisplayDTO?{

        val recipeDisplayDTO = recipe.toDisplayDTO()
        recipeDisplayDTO.like = favoriteService.isRecipeLiked(userEntity,recipe)

        val listComposeEntity = getComposeEntityByRecipe(recipe)
        val listInstruction = instructionService.getInstructionsFromIdRecipeByOrder(recipeDisplayDTO.id!!)

        val listInstructionDTO = instructionsToInstructionsDTO(listInstruction)
        val listIngredientDTO = composesEntityToIngredientsDTO(listComposeEntity)

        recipeDisplayDTO.listIngredientDTO = listIngredientDTO
        recipeDisplayDTO.listInstructionDTO = listInstructionDTO

        return recipeDisplayDTO

    }

    fun getComposeEntityByRecipe(recipeEntity:RecipeEntity):List<ComposeEntity>{
        val optionalListCompose = composeRepository.findComposeEntityByRecipeByIdRecipe(recipeEntity)
        if(optionalListCompose.isPresent) {
            println("list compose present")
            return optionalListCompose.get()
        }else{
            throw Exception("List Compose not found")
        }
    }

    private fun instructionsToInstructionsDTO(listInstruction:List<InstructionEntity>):List<InstructionDTO>{
        val listInstructionDTO = emptyList<InstructionDTO>().toMutableList()
        listInstruction.forEach() {
            listInstructionDTO += it.toDTO()
        }
        return listInstructionDTO
    }

    private fun composesEntityToIngredientsDTO(listComposeEntity: List<ComposeEntity>):MutableList<IngredientDTO>{
        val listIngredientDTO = emptyList<IngredientDTO>().toMutableList()
        listComposeEntity.forEach() {
            listIngredientDTO += it.toDTO()
        }
        return listIngredientDTO
    }
}