package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "total_amount", nullable = false)
    val totalAmount: Long,

    @Column(name = "order_date", nullable = false)
    val orderDate: LocalDateTime,
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}