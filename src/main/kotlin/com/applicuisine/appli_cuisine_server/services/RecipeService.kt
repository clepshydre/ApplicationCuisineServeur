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
            val userEntity = userService.getUser(sessionId)
            return composeService.getRecipeToDisplay(recipe.get(),userEntity)
        }else{
            throw Exception("Recipe not found")
        }
    }

    fun getFavoriteRecipesFromIdSession(idSession: String):List<RecipeEntity> {
        val user = userService.getUser(idSession)
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
        val user = userService.getUser(idSession)
        val recipe = recipeRepository.getReferenceById(idRecipe)
        favoriteService.likeRecipe(user,recipe)
    }

    fun unlikeRecipe(idRecipe: Int, idSession: String) {
        val user = userService.getUser(idSession)
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
            if(recipeEntity.id != null) {
                //Create compose
                val composesDTO = recipeToCreate.listComposeDTO
                composeService.create(composesDTO, recipeEntity)

                //Create instruction
                val instructionsDTO = recipeToCreate.listInstructionDTO
                instructionService.create(instructionsDTO, recipeEntity.id!!)
            }else{
                throw Exception("Recipe Entity id is null")
            }
        }else{
            throw Exception("Session Id is null")
        }
    }

    fun create(sessionId:String, recipe:RecipeEntity): RecipeEntity {
        verifyRecipeToCreate(recipe)
        val user = userService.getUser(sessionId)
        recipe.idUser = user.id
        return recipeRepository.save(recipe)
    }

    private fun verifyRecipeToCreate(recipe: RecipeEntity){
        if(!recipe.name.isNullOrEmpty()) {
            if(recipe.cookingTime !=null) {
                if(recipe.preparationTime != null) {
                    if(recipe.waitingTime != null) {
                        if(recipe.cost != null) {
                            if(recipe.cost!! in 1..3) {
                                if(recipe.difficulty != null) {
                                    if(recipe.difficulty!! in 1..3) {

                                    }else{
                                        throw Exception("Difficulty isn't in range 1 to 3")
                                    }
                                }else{
                                    throw Exception("Difficulty is null")
                                }
                            }else{
                                throw Exception("Cost is not in range 1 to 3")
                            }
                        }else{
                            throw Exception("Cost is null")
                        }
                    }else{
                        throw Exception("Waiting Time is null")
                    }
                }else{
                    throw Exception("Cooking time is null")
                }
            }else{
                throw Exception("Preparation time is null")
            }
        }else{
            throw Exception("name is null or empty")
        }
    }
}