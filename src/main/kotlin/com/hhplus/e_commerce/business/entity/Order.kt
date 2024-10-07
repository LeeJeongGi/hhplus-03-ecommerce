package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "payment_date", nullable = false)
    val paymentDate: LocalDateTime,

    @Column(name = "total_amount", nullable = false)
    val totalAmount: Long,
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}