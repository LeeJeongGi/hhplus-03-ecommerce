package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(name = "balance_history")
class BalanceHistory(
    @Column(name = "balance_id", nullable = false)
    val balanceId: Long,

    @Column(name = "change_amount", nullable = false)
    val changeAmount: Int,

    @Column(name = "balance_after", nullable = false)
    val balanceAfter: Int,

    @Column(name = "change_type", length = 50, nullable = false)
    val changeType: String,

    @Column(name = "description", length = 255)
    val description: String? = null,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}