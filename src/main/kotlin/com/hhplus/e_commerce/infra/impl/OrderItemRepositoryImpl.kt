package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.OrderItem
import com.hhplus.e_commerce.business.repository.OrderItemRepository
import com.hhplus.e_commerce.infra.jpa.OrderItemJpaRepository
import org.springframework.stereotype.Repository

@Repository
class OrderItemRepositoryImpl(
    private val orderItemJpaRepository: OrderItemJpaRepository
): OrderItemRepository {

    override fun save(orderItem: OrderItem): OrderItem {
        return orderItemJpaRepository.save(orderItem)
    }

    override fun deleteAll() {
        orderItemJpaRepository.deleteAll()
    }

    override fun saveAll(orderItems: List<OrderItem>) {
        orderItemJpaRepository.saveAll(orderItems)
    }

    override fun findByProductStockId(productStockId: Long): List<OrderItem> {
        return orderItemJpaRepository.findByProductStockId(productStockId)
    }
}