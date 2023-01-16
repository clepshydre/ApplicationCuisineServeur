package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.dto.*
import com.applicuisine.appli_cuisine_server.entities.FavoritedEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import com.applicuisine.appli_cuisine_server.repositories.RecipeRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(val recipeRepository: RecipeRepository, val composeService: ComposeService, val userService: UserService, val favoriteService: FavoriteService, val instructionService: InstructionService){

    fun getAllRecipes():List<RecipeEntity>{
        return recipeRepository.findAll()
    }

    fun getOneRecipe(recipeId: Int): RecipeEntity {

        val optionalRecipeEntity = recipeRepository.findById(recipeId)
        if(optionalRecipeEntity.isPresent) {
            return optionalRecipeEntity.get()
        }else{
            throw Exception("Recipe not found")
        }
    }

    fun getRecipeToDisplay(idRecipe:Int, sessionId :String): RecipeDisplayDTO?{
        val recipe = recipeRepository.findById(idRecipe)
        if (recipe.isPresent) {
            println("Recipe present")
            val userEntity = userService.getActualUser(sessionId)
            return composeService.getRecipeToDisplay(recipe.get(),userEntity)
        }else{
            throw Exception("Recipe not found")
        }
    }

    fun getFavoriteRecipesFromIdSession(idSession: String):List<RecipeEntity> {
        val user = userService.getActualUser(idSession)
        val listFavoritesEntity = favoriteService.getFavoriteRecipeByUserEntity(user)
        return favoritesToRecipesEntity(listFavoritesEntity)
    }

    fun getRecipesForRVHome(idSession: String):List<RecipeRVDTO>{
        return recipesEntityToRecipesRVDTO(getAllNotLikedRecipes(idSession),like = false)
    }

    fun getRecipesForRVFavorite(idSession: String):List<RecipeRVDTO>{
        return recipesEntityToRecipesRVDTO(getFavoriteRecipesFromIdSession(idSession),like = true)
    }

    private fun favoritesToRecipesEntity(listFavorites: List<FavoritedEntity>): List<RecipeEntity>{
        val listRecipeEntity = emptyList<RecipeEntity>().toMutableList()
        listFavorites.forEach(){
            val recipeEntity =getOneRecipe(it.recipeByIdRecipe?.id!!)
            listRecipeEntity += recipeEntity
        }
        return listRecipeEntity
    }

    private fun recipesEntityToRecipesRVDTO(listRecipeEntity: List<RecipeEntity>, like:Boolean): List<RecipeRVDTO>{
        val listRecipeRVDTO = emptyList<RecipeRVDTO>().toMutableList()
        listRecipeEntity.forEach(){ recipeEntity ->
            val recipeRVDTO = recipeEntity.toRVDTO()
            recipeRVDTO.like = like
            listRecipeRVDTO += recipeRVDTO
        }
        return listRecipeRVDTO
    }

    fun likeRecipe(idRecipe: Int, idSession: String) {
        val user = userService.getActualUser(idSession)
        val recipe = recipeRepository.getReferenceById(idRecipe)
        favoriteService.likeRecipe(user,recipe)
    }

    fun unlikeRecipe(idRecipe: Int, idSession: String) {
        val user = userService.getActualUser(idSession)
        val recipe = recipeRepository.getReferenceById(idRecipe)
        favoriteService.unlikeRecipe(user,recipe)
    }

    fun getAllNotLikedRecipes(idSession: String): List<RecipeEntity> {
        val listAllRecipes = getAllRecipes().toMutableList()
        val listAllFavoriteRecipes = getFavoriteRecipesFromIdSession(idSession)
        listAllFavoriteRecipes.forEach{
            if(listAllRecipes.contains(it)){
                listAllRecipes.remove(it)
            }
        }
        return listAllRecipes.toList()
    }

    fun createRecipeWithComposeAndInstructions(recipeToCreate: RecipeDisplayDTO, sessionId: String?) {
        if(sessionId != null) {
            //Create Recipe
            var recipeEntity = recipeToCreate.toEntity()
            recipeEntity = create(sessionId,recipeEntity)

            //Create compose
            val composesDTO = recipeToCreate.listComposeDTO
            composeService.create(composesDTO, recipeEntity)

            //Create instruction
            val instructionsDTO = recipeToCreate.listInstructionDTO
            if(recipeEntity.id != null) {
                instructionService.create(instructionsDTO, recipeEntity.id!!)
            }else{
                throw Exception("Recipe Entity id is null")
            }
        }else{
            throw Exception("Session Id is null")
        }
    }

    fun create(sessionId:String, recipe:RecipeEntity): RecipeEntity {
            val user =  userService.getActualUser(sessionId)
            recipe.idUser = user.id
            return recipeRepository.save(recipe)
    }
}