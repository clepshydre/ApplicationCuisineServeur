package com.applicuisine.appli_cuisine_server.repositories;

import com.applicuisine.appli_cuisine_server.entities.UnitEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UnitRepository : JpaRepository<UnitEntity, Int> {
    fun findByName(unitName:String): Optional<UnitEntity>
}