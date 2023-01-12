package com.applicuisine.appli_cuisine_server.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "ingredient", schema = "cuisine_db", catalog = "")
class IngredientEntity (

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ingredient", nullable = false)
    var id: Int? = null,

    @Basic
    @Column(name = "name_ingredient", nullable = false, length = 200)
    var name: String? = null,

    @OneToMany(mappedBy = "ingredientByIdIngredient")
    @JsonIgnore
    var composesByIdIngredient: Collection<ComposeEntity>? = null

)