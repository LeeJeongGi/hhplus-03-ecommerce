package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "status", nullable = false)
    val status: String,

    @Column(name = "total_amount", nullable = false)
    val totalAmount: Int,

    @Column(name = "order_date", nullable = false)
    val orderDate: LocalDateTime,
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

}