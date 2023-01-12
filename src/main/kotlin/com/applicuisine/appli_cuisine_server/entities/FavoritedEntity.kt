package com.applicuisine.appli_cuisine_server.entities

import jakarta.persistence.*

@Entity
@Table(name = "favorited", schema = "cuisinebdd")
class FavoritedEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_favorited", nullable = false)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "id_recipe", referencedColumnName = "id_recipe", nullable = false)
    var recipeByIdRecipe: RecipeEntity? = null,

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    var usersByIdUser: UserEntity? = null
)