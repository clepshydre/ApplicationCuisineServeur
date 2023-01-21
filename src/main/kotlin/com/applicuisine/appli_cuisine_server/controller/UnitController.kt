package com.applicuisine.appli_cuisine_server.controller

import com.applicuisine.appli_cuisine_server.services.IngredientService
import com.applicuisine.appli_cuisine_server.services.UnitService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("unit")
class UnitController(val unitService: UnitService) {
    @GetMapping("/getAllUnits")
    fun getAllUnits():Any{
        try{
            println("/getAllUnits")
            return unitService.getAllUnits()
        }catch (e:Exception){
            e.printStackTrace()
            return e
        }
    }

}