package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.Product
import com.hhplus.e_commerce.business.entity.ProductStock
import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.infra.jpa.ProductJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository
): ProductRepository {

    override fun save(product: Product): Product {
        return productJpaRepository.save(product)
    }

    override fun findById(productId: Long): Product? = productJpaRepository.findById(productId).getOrNull()

    override fun deleteAll() {
        productJpaRepository.deleteAll()
    }

    override fun findStocksByProductId(productId: Long): List<ProductStock> {
        return productJpaRepository.findStocksByProductId(productId)
    }

    override fun findAllProduct(pageable: Pageable): Page<Product> {
        return productJpaRepository.findAll(pageable)
    }
}