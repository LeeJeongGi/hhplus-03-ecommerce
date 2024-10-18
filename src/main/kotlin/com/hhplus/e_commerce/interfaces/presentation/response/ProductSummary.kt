package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.business.dto.ProductStatsDto

data class ProductSummary(
    val productId: Long,
    val name: String,
    val category: String,
    val price: Int,
    val salesCount: Int
) {
    companion object {
        fun from(productStatsDto: ProductStatsDto): ProductSummary {
            return ProductSummary(
                productId = productStatsDto.productId,
                name = productStatsDto.name,
                category = productStatsDto.category,
                price = productStatsDto.price,
                salesCount = productStatsDto.salesCount
            )
        }
    }
}
