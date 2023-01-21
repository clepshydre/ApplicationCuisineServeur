package com.applicuisine.appli_cuisine_server.services

import com.applicuisine.appli_cuisine_server.entities.UnitEntity
import com.applicuisine.appli_cuisine_server.repositories.UnitRepository
import org.springframework.stereotype.Service

@Service
class UnitService(val unitRepository: UnitRepository) {

    fun find(name:String): UnitEntity {
        val optionalUnit = unitRepository.findByName(name)
        if(optionalUnit.isPresent){
            return optionalUnit.get()
        }else{
            throw Exception("Unit couldn't be find with this name: $name")
        }
    }

    fun getAllUnits(): List<UnitEntity> {
        return unitRepository.findAll()
    }

}