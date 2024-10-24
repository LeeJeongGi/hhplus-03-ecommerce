package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.ProductStock
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface ProductStockJpaRepository: JpaRepository<ProductStock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductStock p WHERE p.id = :productStockId")
    fun findByIdWithLock(productStockId: Long): ProductStock?
}