package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Carts
import org.springframework.data.jpa.repository.JpaRepository

interface CartsJpaRepository: JpaRepository<Carts, Long> {

    fun findByUserId(userId: Long): List<Carts>

    fun findByUserIdAndProductId(userId: Long, productId: Long): Carts?
}