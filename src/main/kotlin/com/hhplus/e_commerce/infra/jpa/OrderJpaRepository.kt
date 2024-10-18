package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Order
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface OrderJpaRepository: JpaRepository<Order, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.id = :orderId")
    fun findByOrderIdWithLock(orderId: Long): Order?
}