package com.applicuisine.appli_cuisine_server.entities

import jakarta.persistence.*

@Entity
@Table(name = "compose", schema = "cuisine_db", catalog = "")
class ComposeEntity (
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_compose", nullable = false)
    var idCompose: Int? = null,

    @Basic
    @Column(name = "quantity", nullable = false, precision = 0)
    var quantity: Double? = null,

    @ManyToOne
    @JoinColumn(name = "id_recipe", referencedColumnName = "id_recipe", nullable = false)
    var recipeByIdRecipe: RecipeEntity? = null,

    @ManyToOne
    @JoinColumn(name = "id_unit", referencedColumnName = "id_unit")
    var unitByIdUnit: UnitEntity? = null,

    @ManyToOne
    @JoinColumn(name = "id_ingredient", referencedColumnName = "id_ingredient", nullable = false)
    var ingredientByIdIngredient: IngredientEntity? = null
)