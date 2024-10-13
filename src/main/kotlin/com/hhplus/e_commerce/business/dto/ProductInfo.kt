package com.hhplus.e_commerce.business.dto

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
