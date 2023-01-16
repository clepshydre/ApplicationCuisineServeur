package com.applicuisine.appli_cuisine_server.dto

import com.applicuisine.appli_cuisine_server.entities.InstructionEntity

class InstructionDTO(

    var instruction:String? = ""
)

fun InstructionEntity.toDTO() = InstructionDTO(
    instruction = instruction
)

fun InstructionDTO.toEntity(idRecipe: Int, order:Int) = InstructionEntity(
    id = null,
    instruction = instruction,
    order = order,
    idRecipe = idRecipe
)