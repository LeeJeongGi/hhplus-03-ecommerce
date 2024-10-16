package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.ProductOrderStats
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface ProductOrderStatsRepository {

    fun save(productOrderStats: ProductOrderStats): ProductOrderStats

    fun deleteAll()

    fun getTop5Products(lastWeekDate: LocalDateTime, pageable: Pageable): List<ProductOrderStats>

}