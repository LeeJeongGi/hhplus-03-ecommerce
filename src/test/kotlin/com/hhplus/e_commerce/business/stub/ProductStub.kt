package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.Product

object ProductStub {

    fun create(
        name: String = "Lee",
        category: String = "TOP",
        price: Int = 1000,
    ): Product {
        return Product(
            name = name,
            category = category,
            price = price,
        )
    }
}