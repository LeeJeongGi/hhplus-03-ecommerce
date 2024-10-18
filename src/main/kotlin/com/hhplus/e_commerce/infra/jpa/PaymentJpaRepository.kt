package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository: JpaRepository<Payment, Long> {
}