package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Carts

data class CartsSaveResultDto(
    val userId: Long,
    val productId: Long,
) {
    companion object {
        fun from(saveCarts: Carts): CartsSaveResultDto {
            return CartsSaveResultDto(
                userId = saveCarts.userId,
                productId = saveCarts.productId
            )
        }
    }

}
