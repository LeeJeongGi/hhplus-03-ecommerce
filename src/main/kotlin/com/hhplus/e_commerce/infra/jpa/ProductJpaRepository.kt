package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Product
import com.hhplus.e_commerce.business.entity.ProductStock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductJpaRepository: JpaRepository<Product, Long> {

    @Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId")
    fun findStocksByProductId(@Param("productId") productId: Long): List<ProductStock>

    @Query("SELECT p FROM Product p")
    fun findAllProduct(pageable: Pageable): Page<Product>
}