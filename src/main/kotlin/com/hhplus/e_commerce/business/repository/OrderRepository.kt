package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.Order

interface OrderRepository {

    fun save(order: Order): Order

    fun findById(orderId: Long): Order?

    fun deleteAll()

    fun findByOrderIdWithLock(orderId: Long): Order?

}