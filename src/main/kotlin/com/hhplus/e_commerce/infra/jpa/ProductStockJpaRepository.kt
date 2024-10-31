package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.ProductStock
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductStockJpaRepository: JpaRepository<ProductStock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductStock p WHERE p.id = :productStockId")
    fun findByIdWithLock(productStockId: Long): ProductStock?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductStock p WHERE p.id IN :productStockIds")
    fun findByIdsWithLock(@Param("productStockIds") productStockIds: List<Long>): List<ProductStock>

    @Query("SELECT p FROM ProductStock p WHERE p.id IN :productStockIds")
    fun findByProductIds(@Param("productStockIds") productStockIds: List<Long>): List<ProductStock>

    @Query("SELECT p.id FROM ProductStock p WHERE p.id IN :productStockIds")
    fun findExistingIds(productStockIds: List<Long>): List<Long>
}