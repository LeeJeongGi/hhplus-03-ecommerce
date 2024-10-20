package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(
    name: String,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set
}