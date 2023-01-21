package com.applicuisine.appli_cuisine_server.controller

import com.applicuisine.appli_cuisine_server.services.IngredientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ingredient")
class IngredientController(val ingredientService: IngredientService) {

    @GetMapping("/getAllIngredients")
    fun getAllIngredients():Any{
        try{
            println("/getAllIngredients")
            return ingredientService.getAllIngredients()
        }catch (e:Exception){
            e.printStackTrace()
            return e
        }
    }
}