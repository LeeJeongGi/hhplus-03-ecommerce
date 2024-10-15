package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.Payment
import com.hhplus.e_commerce.business.repository.PaymentRepository
import com.hhplus.e_commerce.infra.jpa.PaymentJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
): PaymentRepository {
    override fun save(payment: Payment): Payment {
        return paymentJpaRepository.save(payment)
    }

    override fun deleteAll() {
        paymentJpaRepository.deleteAll()
    }

}