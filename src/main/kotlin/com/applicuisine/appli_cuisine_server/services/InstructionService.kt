package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.dto.InstructionDTO
import com.applicuisine.appli_cuisine_server.dto.toEntity
import com.applicuisine.appli_cuisine_server.entities.InstructionEntity
import com.applicuisine.appli_cuisine_server.repositories.InstructionRepository
import org.springframework.stereotype.Service

@Service
class InstructionService(val instructionRepository: InstructionRepository) {

    fun getInstructionsFromIdRecipeByOrder(idRecipe:Int):List<InstructionEntity>{

        val optionalListInstruction = instructionRepository.findAllByIdRecipeOrderByOrder(idRecipe)
        if(optionalListInstruction.isPresent){
            return optionalListInstruction.get()
        }else{
            throw Exception("Instructions not found")
        }

    }
    fun create(instructionsDTO: List<InstructionDTO>, idRecipe: Int) {
        var order = 1
        instructionsDTO.forEach{ instructionDTO ->
            create(instructionDTO, idRecipe, order)
            order++
        }
    }

    fun create(instructionDTO: InstructionDTO, idRecipe: Int, order:Int){
        val instructionEntity = instructionDTO.toEntity(idRecipe, order)
        create(instructionEntity)
    }

    fun create(instruction: InstructionEntity): InstructionEntity{
        return instructionRepository.save(instruction)
    }




}
