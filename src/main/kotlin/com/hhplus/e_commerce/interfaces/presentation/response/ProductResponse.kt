package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.business.dto.ProductInfo
import com.hhplus.e_commerce.business.dto.StockQuantity

data class ProductResponse(
    val productId: Long,
    val name: String,
    val price: Int,
    val stockQuantities: List<StockQuantity>
) {
    companion object {
        fun from(productInfo: ProductInfo): ProductResponse {
            return ProductResponse(
                productId = productInfo.productId,
                name = productInfo.name,
                price = productInfo.price,
                stockQuantities = productInfo.stockQuantities
            )
        }
    }
}
