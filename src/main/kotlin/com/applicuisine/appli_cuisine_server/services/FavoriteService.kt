package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.entities.FavoritedEntity
import com.applicuisine.appli_cuisine_server.entities.RecipeEntity
import com.applicuisine.appli_cuisine_server.entities.UserEntity
import com.applicuisine.appli_cuisine_server.repositories.FavoritedRepository
import org.springframework.stereotype.Service

@Service
class FavoriteService(val favoritedRepository: FavoritedRepository) {

    fun getFavoriteRecipeByUserEntity(userEntity: UserEntity):List<FavoritedEntity>{
        val optionalListFavorited = favoritedRepository.findAllByUsersByIdUser(userEntity)
        if(optionalListFavorited.isPresent) {
            return optionalListFavorited.get()
        }else{
            throw Exception("Favorite not found")
        }
    }

    fun likeRecipe(userEntity: UserEntity, recipeEntity: RecipeEntity) {
        val favoriteEntity = FavoritedEntity(usersByIdUser = userEntity, recipeByIdRecipe = recipeEntity )
        favoritedRepository.save(favoriteEntity)
    }

    fun unlikeRecipe(user: UserEntity, recipe: RecipeEntity) {
        val optionalFavoriteEntity = favoritedRepository.findByUsersByIdUserAndRecipeByIdRecipe(user, recipe)
        if(optionalFavoriteEntity.isPresent) {
            val favoriteEntity = optionalFavoriteEntity.get()
            favoritedRepository.delete(favoriteEntity)
        }else{
            throw Exception("Favorite doesn't exist")
        }
    }

    fun isRecipeLiked(userEntity: UserEntity, recipeEntity: RecipeEntity):Boolean{
        val optionalFavorite = favoritedRepository.findByUsersByIdUserAndRecipeByIdRecipe(userEntity,recipeEntity)
        return optionalFavorite.isPresent
    }


}