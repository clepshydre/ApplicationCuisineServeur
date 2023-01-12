package com.applicuisine.appli_cuisine_server.repositories;

import com.applicuisine.appli_cuisine_server.entities.InstructionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface InstructionEntityRepository : JpaRepository<InstructionEntity, Int> {

    fun findAllByIdRecipeOrderByOrder(idRecipe:Int): Optional<List<InstructionEntity>>
}