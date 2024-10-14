package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.Order
import com.hhplus.e_commerce.business.repository.OrderRepository
import com.hhplus.e_commerce.infra.jpa.OrderJpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository
): OrderRepository {

    override fun save(order: Order): Order {
        return orderJpaRepository.save(order)
    }

    override fun findById(orderId: Long): Order? = orderJpaRepository.findById(orderId).getOrNull()

    override fun deleteAll() {
        orderJpaRepository.deleteAll()
    }
}