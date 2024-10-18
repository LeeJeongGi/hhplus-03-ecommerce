package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderItemJpaRepository: JpaRepository<OrderItem, Long> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.productStockId = :productStockId")
    fun findByProductStockId(@Param("productStockId") productStockId: Long): List<OrderItem>
}