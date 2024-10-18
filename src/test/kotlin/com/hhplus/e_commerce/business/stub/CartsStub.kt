package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.Carts

object CartsStub {

    fun create(
        userId: Long = 1L,
        productId: Long = 1L,
    ): Carts {
        return Carts(
            userId = userId,
            productId = productId,
        )
    }
}