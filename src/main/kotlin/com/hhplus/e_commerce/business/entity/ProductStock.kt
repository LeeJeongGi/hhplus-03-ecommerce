package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "product_stock",
    indexes = [
        Index(name = "idx_product_id", columnList = "product_id"),
        Index(name = "idx_size", columnList = "size")
    ]
)
class ProductStock(
    @Column(name = "product_id", nullable = false)
    val productId: Long,

    @Column(name = "size", nullable = false)
    val size: String,

    quantity: Int,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0

    @Column(name = "quantity", nullable = false)
    var quantity: Int = quantity
        private set

    fun updateQuantity(newQuantity: Int) {
        this.quantity = newQuantity
    }
}