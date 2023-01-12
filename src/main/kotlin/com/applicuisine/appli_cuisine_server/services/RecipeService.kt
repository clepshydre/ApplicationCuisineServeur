package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.dto.RecipeDisplayDTO
import com.applicuisine.appli_cuisine_server.dto.RecipeRVDTO
import com.applicuisine.appli_cuisine_server.dto.toRVDTO
import com.applicuisine.appli_cuisine_server.entities.FavoritedEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import com.applicuisine.appli_cuisine_server.repositories.RecipeRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(val recipeRepository: RecipeRepository, val composeService: ComposeService, val userService: UserService, val favoriteService: FavoriteService){

    fun getAllRecipes():List<RecipeEntity>{
        return recipeRepository.findAll()
    }

    fun getOneRecipeById(recipeId: Int): RecipeEntity {

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
        return listFavoritesToListRecipeEntity(listFavoritesEntity)
    }

    fun getRecipesForRVHome(idSession: String):List<RecipeRVDTO>{
        return listRecipeEntityToListRecipeRVDTO(getAllNotLikedRecipes(idSession),like = false)
    }

    fun getRecipesForRVFavorite(idSession: String):List<RecipeRVDTO>{
        return listRecipeEntityToListRecipeRVDTO(getFavoriteRecipesFromIdSession(idSession),like = true)
    }

    private fun listFavoritesToListRecipeEntity(listFavorites: List<FavoritedEntity>): List<RecipeEntity>{
        val listRecipeEntity = emptyList<RecipeEntity>().toMutableList()
        listFavorites.forEach(){
            val recipeEntity =getOneRecipeById(it.recipeByIdRecipe?.id!!)
            listRecipeEntity += recipeEntity
        }
        return listRecipeEntity
    }

    private fun listRecipeEntityToListRecipeRVDTO(listRecipeEntity: List<RecipeEntity>, like:Boolean): List<RecipeRVDTO>{
        val listRecipeRVDTO = emptyList<RecipeRVDTO>().toMutableList()
        listRecipeEntity.forEach(){
            val recipeRVDTO = it.toRVDTO()
            recipeRVDTO.like = like
            listRecipeRVDTO += recipeRVDTO
        }
        return listRecipeRVDTO
    }

    fun likeRecipeFromIdRecipeAndSessionId(idRecipe: Int, idSession: String) {
        val user = userService.getActualUser(idSession)
        val recipe = recipeRepository.getReferenceById(idRecipe)
        favoriteService.likeRecipe(user,recipe)
    }

    fun unlikeRecipeFromIdRecipeAndSessionId(idRecipe: Int, idSession: String) {
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

    //TODO retourner la liste order by likes
//    fun getAllRecipesByLikes(): List<RecipeEntity> {
//
//        return recipeRepository.
//    }
}