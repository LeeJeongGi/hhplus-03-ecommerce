package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.Product
import com.hhplus.e_commerce.business.entity.ProductStock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepository {

    fun save(product: Product): Product

    fun findById(productId: Long): Product?

    fun deleteAll()

    fun findStocksByProductId(productId: Long): List<ProductStock>

    fun findAllProduct(pageable: Pageable): Page<Product>
}