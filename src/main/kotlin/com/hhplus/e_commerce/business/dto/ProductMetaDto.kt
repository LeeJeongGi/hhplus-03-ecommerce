package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Product
import java.io.Serializable

data class ProductMetaDto(
    val productId: Long,
    val name: String,
    val price: Int,
) : Serializable {
    companion object {
        fun from(product: Product): ProductMetaDto {
            return ProductMetaDto(
                productId = product.id,
                name = product.name,
                price = product.price,
            )
        }
    }
}
