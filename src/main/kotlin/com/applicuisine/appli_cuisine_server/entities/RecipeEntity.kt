package com.applicuisine.appli_cuisine_server.entities

import jakarta.persistence.*
import java.sql.Time

@Entity
@Table(name = "recipe", schema = "cuisinebdd")
class RecipeEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_recipe", nullable = false)
    var id: Int? = null,

    @Basic
    @Column(name = "name_recipe", nullable = false, length = 1000)
    var name: String? = null,

    @Basic
    @Column(name = "preparation_time_recipe", nullable = false)
    var preparationTime: Int? = null,

    @Basic
    @Column(name = "cooking_time_recipe", nullable = false)
    var cookingTime: Int? = null,

    @Basic
    @Column(name = "waiting_time_recipe", nullable = false)
    var waitingTime: Int? = null,

    @Basic
    @Column(name = "difficulty_recipe", nullable = false)
    var difficulty: Int? = null,

    @Basic
    @Column(name = "cost_recipe", nullable = false)
    var cost: Int? = null,

    @Basic
    @Column(name = "image_recipe", nullable = true, length = 1000)
    var image: String? = null
)