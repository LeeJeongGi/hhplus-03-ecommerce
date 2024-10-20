package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_item")
class OrderItem(

    @Column(name = "order_id", nullable = false)
    val orderId: Long,

    @Column(name = "product_stock_id", nullable = false)
    val productStockId: Long,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}