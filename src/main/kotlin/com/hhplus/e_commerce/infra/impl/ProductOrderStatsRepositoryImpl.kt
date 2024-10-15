package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.ProductOrderStats
import com.hhplus.e_commerce.business.repository.ProductOrderStatsRepository
import com.hhplus.e_commerce.infra.jpa.ProductOrderStatJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.time.LocalDate
import java.time.LocalDateTime

@Repository
class ProductOrderStatsRepositoryImpl(
    private val productOrderStatsJpaRepository: ProductOrderStatJpaRepository
): ProductOrderStatsRepository {

    override fun save(productOrderStats: ProductOrderStats): ProductOrderStats {
        return productOrderStatsJpaRepository.save(productOrderStats)
    }

    override fun deleteAll() {
        productOrderStatsJpaRepository.deleteAll()
    }

    override fun getTop5Products(lastWeekDate: LocalDateTime, pageable: Pageable): List<ProductOrderStats> {
        return productOrderStatsJpaRepository.findTop5Products(lastWeekDate, pageable)
    }

}