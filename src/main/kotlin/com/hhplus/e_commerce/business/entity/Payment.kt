package com.hhplus.e_commerce.business.entity

import com.hhplus.e_commerce.business.dto.PaymentSaveDto
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment")
class Payment(

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "order_id", nullable = false)
    val orderId: Long,

    @Column(name = "status", nullable = false, length = 50)
    val status: String,

    @Column(name = "amount", nullable = false)
    val amount: Int,

    @Column(name = "payment_date", nullable = false)
    val paymentDate: LocalDateTime = LocalDateTime.now()

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    companion object {
        fun from(paymentSaveDto: PaymentSaveDto): Payment {
            return Payment(
                userId = paymentSaveDto.userId,
                orderId = paymentSaveDto.orderId,
                amount = paymentSaveDto.amount,
                status = paymentSaveDto.status,
                paymentDate = LocalDateTime.now(),
            )
        }
    }
}