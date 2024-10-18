package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.business.dto.CartsSaveResultDto
import com.hhplus.e_commerce.business.entity.Carts

data class CartsResponse(
    val userId: Long,
    val productId: Long,
) {
    companion object {
        fun from(cartsSaveDto: CartsSaveResultDto): CartsResponse {
            return CartsResponse(
                userId = cartsSaveDto.userId,
                productId = cartsSaveDto.productId,
            )
        }

        fun from(carts: Carts): CartsResponse {
            return CartsResponse(
                userId = carts.userId,
                productId = carts.productId,
            )
        }
    }
}
