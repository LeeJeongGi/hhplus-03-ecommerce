package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.ProductStock
import com.hhplus.e_commerce.business.repository.ProductStockRepository
import com.hhplus.e_commerce.infra.jpa.ProductStockJpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductStockRepositoryImpl(
    private val productStockJpaRepository: ProductStockJpaRepository
): ProductStockRepository {

    override fun save(productStock: ProductStock): ProductStock {
        return productStockJpaRepository.save(productStock)
    }

    override fun findById(productStockId: Long): ProductStock? {
        return productStockJpaRepository.findById(productStockId).getOrNull()
    }

    override fun findByIdWithLock(productStockId: Long): ProductStock? {
        return productStockJpaRepository.findByIdWithLock(productStockId)
    }

    override fun saveAll(productStocks: List<ProductStock>) {
        productStockJpaRepository.saveAll(productStocks)
    }

    override fun deleteAll() {
        productStockJpaRepository.deleteAll()
    }

}