package com.applicuisine.appli_cuisine_server.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "unit", schema = "cuisine_db", catalog = "")
class UnitEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_unit", nullable = false)
    var id: Int? = null,

    @Basic
    @Column(name = "name_unit", nullable = false, length = 200)
    var name: String? = null,

    @OneToMany(mappedBy = "unitByIdUnit")
    @JsonIgnore
    var composesByIdUnit: Collection<ComposeEntity>? = null
)