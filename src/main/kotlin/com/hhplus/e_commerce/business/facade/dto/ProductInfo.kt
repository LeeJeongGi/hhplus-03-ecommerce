package com.hhplus.e_commerce.business.facade.dto

import com.hhplus.e_commerce.business.dto.ProductMetaDto
import com.hhplus.e_commerce.business.dto.ProductSubDto
import com.hhplus.e_commerce.business.dto.StockQuantity

data class ProductInfo(
    val productId: Long,
    val name: String,
    val price: Int,
    val stockQuantities: List<StockQuantity>
) {
    companion object {
        fun of(product: ProductMetaDto, stocks: List<ProductSubDto>): ProductInfo {
            return ProductInfo(
                productId = product.productId,
                name = product.name,
                price = product.price,
                stockQuantities = stocks.map { StockQuantity(it.size, it.quantity) }
            )
        }
    }

}
