package com.hhplus.e_commerce.business.dto

data class CartsSaveDto(
    val userId: Long,
    val productId: Long,
) {
    companion object {
        fun of(userId: Long, productId: Long): CartsSaveDto {
            return CartsSaveDto(
                userId = userId,
                productId = productId,
            )
        }
    }
}
