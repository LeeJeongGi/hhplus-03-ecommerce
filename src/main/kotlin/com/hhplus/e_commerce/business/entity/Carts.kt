package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(name = "carts")
class Carts(

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "product_id", nullable = false)
    val productId: Long,


): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}