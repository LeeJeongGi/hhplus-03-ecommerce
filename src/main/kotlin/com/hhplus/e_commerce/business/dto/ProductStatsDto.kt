package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.interfaces.presentation.response.ProductSummary

data class ProductStatsDto(
    val productId: Long,
    val name: String,
    val category: String,
    val price: Int,
    val salesCount: Int
) {
    companion object {
        fun from(productSummary: ProductSummary): ProductStatsDto {
            return ProductStatsDto(
                productId = productSummary.productId,
                name = productSummary.name,
                category = productSummary.category,
                price = productSummary.price,
                salesCount = productSummary.salesCount
            )
        }
    }

}
