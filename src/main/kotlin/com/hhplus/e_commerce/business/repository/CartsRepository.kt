package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.Carts

interface CartsRepository {

    fun save(carts: Carts): Carts

    fun findByUserId(userId: Long): List<Carts>

    fun findByUserIdAndProductId(userId: Long, productId: Long): Carts?

    fun delete(carts: Carts)

}