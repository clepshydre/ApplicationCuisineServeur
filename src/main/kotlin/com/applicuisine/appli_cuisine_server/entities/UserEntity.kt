package com.applicuisine.appli_cuisine_server.entities

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "users", schema = "cuisinebdd")
class UserEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    var id: Int? = null,

    @Basic
    @Column(name = "mail_user", nullable = false, length = 200)
    var mail: String? = null,

    @Basic
    @Column(name = "password_user", nullable = false, length = 200)
    var password: String? = null,

    @Basic
    @Column(name = "session_id_user", nullable = true, length = 200)
    var sessionId: String? = null,

    @Basic
    @Column(name = "date_of_birth_user", nullable = true, length = 20)
    var dateOfBirth: String? = null,

    @Basic
    @Column(name = "sex_user", nullable = true)
    var sex: Int? = null,

    @Basic
    @Column(name = "cuisine_level_user", nullable = true)
    var cuisineLevel: Int? = null,

    @Basic
    @Column(name = "budget_user", nullable = true)
    var budget: Int? = null
)