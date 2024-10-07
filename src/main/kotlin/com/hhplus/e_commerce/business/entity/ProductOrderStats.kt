package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "product_order_stats")
class ProductOrderStats(

    @Column(name = "product_id", nullable = false)
    val productId: Long,

    @Column(name = "order_date", nullable = false)
    val orderDate: LocalDateTime,

    @Column(name = "total_order", nullable = false)
    val totalOrder: Long,

    @Column(name = "total_sales_amount", nullable = false)
    val totalSalesAmount: Long,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0


}