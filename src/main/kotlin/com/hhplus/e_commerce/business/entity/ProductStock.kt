package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(name = "product_stock")
class ProductStock(
    @Column(name = "product_id", nullable = false)
    val productId: Long,

    quantity: Int,
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0

    @Column(name = "quantity", nullable = false)
    var quantity: Int = quantity
        private set

}