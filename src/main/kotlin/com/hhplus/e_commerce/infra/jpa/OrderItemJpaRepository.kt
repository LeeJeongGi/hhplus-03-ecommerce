package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemJpaRepository: JpaRepository<OrderItem, Long> {

}