package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.ProductOrderStats
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface ProductOrderStatJpaRepository: JpaRepository<ProductOrderStats, Long> {

    @Query("SELECT p FROM ProductOrderStats p WHERE p.orderDate >= :lastWeekDate " +
            "GROUP BY p.productId ORDER BY SUM(p.totalOrder)")
    fun findTop5Products(@Param("lastWeekDate") lastWeekDate: LocalDateTime, pageable: Pageable): List<ProductOrderStats>
}