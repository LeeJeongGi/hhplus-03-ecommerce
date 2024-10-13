package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.Product
import com.hhplus.e_commerce.business.entity.ProductStock

interface ProductRepository {

    fun save(product: Product): Product

    fun findById(productId: Long): Product?

    fun deleteAll()

    fun findStocksByProductId(productId: Long): List<ProductStock>
}