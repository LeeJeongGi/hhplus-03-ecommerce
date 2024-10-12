package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "name")
    val name: String,

    balance: Int,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "balance")
    var balance: Int = balance
        private set
}