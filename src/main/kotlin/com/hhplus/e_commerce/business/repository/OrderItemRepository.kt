package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.OrderItem

interface OrderItemRepository {

    fun save(orderItem: OrderItem): OrderItem

    fun deleteAll()

    fun saveAll(orderItems: List<OrderItem>)

    fun findByProductStockId(productStockId: Long): List<OrderItem>
}