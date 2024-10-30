package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.ProductStock

interface ProductStockRepository {

    fun save(productStock: ProductStock): ProductStock

    fun findById(productStockId: Long): ProductStock?

    fun findByIdWithLock(productStockId: Long): ProductStock?

    fun saveAll(productStocks: List<ProductStock>)

    fun deleteAll()

    fun findByIds(productStockIds: List<Long>): List<ProductStock>

    fun findExistingIds(productStockIds: List<Long>): List<Long>
}