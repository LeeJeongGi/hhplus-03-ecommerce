package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.ProductStock

object ProductStockStub {

    fun create(
        productId: Long = 1L,
        size: String = "M",
        quantity: Int = 1
    ): ProductStock {
        return ProductStock(
            productId = productId,
            size = size,
            quantity = quantity
        )
    }

    fun createMultiple(
        productId: Long = 1L,
        sizesWithQuantities: List<Pair<String, Int>> = listOf(
            "M" to 3,
            "L" to 2,
            "XL" to 1
        )
    ): List<ProductStock> {
        return sizesWithQuantities.map { (size, quantity) ->
            create(productId, size, quantity)
        }
    }

}