package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PaymentJpaRepository: JpaRepository<Payment, Long> {

    @Query("select p from Payment p where p.orderId = :orderId")
    fun findByOrderId(@Param("orderId")orderId: Long): Payment?
}