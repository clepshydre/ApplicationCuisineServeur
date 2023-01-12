package com.applicuisine.appli_cuisine_server.repositories;

import com.applicuisine.appli_cuisine_server.entities.UnitEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UnitEntityRepository : JpaRepository<UnitEntity, Int> {
}