package com.applicuisine.appli_cuisine_server.entities

import jakarta.persistence.*

@Entity
@Table(name = "instruction", schema = "cuisine_db", catalog = "")
class InstructionEntity (

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_instruction", nullable = false)
    var id: Int? = null,

    @Basic
    @Column(name = "description_instruction", nullable = false, length = 200)
    var instruction: String? = null,

    @Basic
    @Column(name = "order_instruction", nullable = false, length = 200)
    var order: Int? = null,

    @Basic
    @Column(name = "id_recipe", nullable = false)
    var idRecipe: Int? = null

)