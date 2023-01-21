package com.applicuisine.appli_cuisine_server.controller

import com.applicuisine.appli_cuisine_server.beans.MyException
import com.applicuisine.appli_cuisine_server.dto.RecipeDisplayDTO
import com.applicuisine.appli_cuisine_server.entities.IngredientEntity
import com.applicuisine.appli_cuisine_server.services.RecipeService
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("recipe")
class RecipeController(val recipeService: RecipeService) {

    @GetMapping("/test")
    fun test(response: HttpServletResponse,httpSession: HttpSession ): Any?{
        try{
            return recipeService.getAllNotLikedRecipes("528C3316ADC162A96852D0D4A7FE7195")
        }catch(e:Exception){
            return e
        }
    }

    @GetMapping("/getAllRecipes")
    fun getAllRecipes(response: HttpServletResponse):Any{
        println("/getAllRecipes")
        try {
            return recipeService.getAllRecipes()
        }catch (e: MyException){
            println(e.errorMessage)
            response.status = 500
            return e
        }
    }

    @GetMapping("/getOneRecipe")
    fun getOneRecipe(recipeId :Int, response: HttpServletResponse):Any{
        println("/getOneRecipe")
        try {
            return recipeService.getOneRecipe(recipeId)
        }catch (e: MyException){
            println(e.errorMessage)
            response.status = 500
            return e
        }
    }

    @PostMapping("getRecipeToDisplay")
    fun getRecipeToDisplay(@RequestBody idRecipe:Int, httpSession: HttpSession):Any?{
        println("/getRecipeToDisplay")
        try {
            return recipeService.getRecipeToDisplay(idRecipe, httpSession.id)
        }catch (e:Exception){
            e.printStackTrace()
            return e
        }
    }

    @GetMapping("/getAllFavoriteRecipes")
    fun getAllFavoriteRecipes(httpSession: HttpSession, response: HttpServletResponse):Any{
        println("/getAllFavoriteRecipes")
        try{
            return recipeService.getRecipesForRVFavorite(httpSession.id)
        }catch (e: MyException){
            println(e.errorMessage)
            response.status = 500
            return e
        }
    }

    @GetMapping("/getAllHomeRecipes")
    fun getAllHomeRecipes(httpSession: HttpSession, response: HttpServletResponse):Any{
        println("/getAllHomeRecipes")
        try{
            return recipeService.getRecipesForRVHome(httpSession.id)
        }catch (e: MyException){
            println(e.errorMessage)
            response.status = 500
            return e
        }
    }

    @PostMapping("/likeRecipe")
    fun likeRecipe(@RequestBody idRecipe: Int,response: HttpServletResponse,httpSession: HttpSession ): Any?{
        try {
            return recipeService.likeRecipe(idRecipe,httpSession.id)
        }catch(e:Exception){
            println(e.message)
            return e
        }
    }

    @PostMapping("/unlikeRecipe")
    fun unlikeRecipe(@RequestBody idRecipe: Int,response: HttpServletResponse,httpSession: HttpSession ): Any?
    {
        try {
            recipeService.unlikeRecipe(idRecipe,httpSession.id)
            return ""
        }catch(e:Exception){
            println(e.message)
            return e
        }
    }

    @PostMapping("/createRecipe")
    fun createRecipe(@RequestBody recipeToCreate : RecipeDisplayDTO, httpSession: HttpSession):Any{
        try {
            recipeService.createRecipeWithComposeAndInstructions(recipeToCreate,httpSession.id)
            return "Recipe created"
        }catch(e:Exception){
            println(e.message)
            return e
        }
    }

}