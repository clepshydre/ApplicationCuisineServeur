package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.entities.InstructionEntity
import com.applicuisine.appli_cuisine_server.repositories.InstructionEntityRepository
import org.springframework.stereotype.Service

@Service
class InstructionService(val instructionRepository: InstructionEntityRepository) {

    fun getInstructionsFromIdRecipeByOrder(idRecipe:Int):List<InstructionEntity>{

        val optionalListInstruction = instructionRepository.findAllByIdRecipeOrderByOrder(idRecipe)
        if(optionalListInstruction.isPresent){
            return optionalListInstruction.get()
        }else{
            throw Exception("Instructions not found")
        }

    }
}
