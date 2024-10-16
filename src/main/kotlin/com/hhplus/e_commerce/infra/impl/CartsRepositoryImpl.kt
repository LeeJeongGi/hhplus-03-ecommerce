package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.Carts
import com.hhplus.e_commerce.business.repository.CartsRepository
import com.hhplus.e_commerce.infra.jpa.CartsJpaRepository
import org.springframework.stereotype.Repository

@Repository
class CartsRepositoryImpl(
    private val cartsJpaRepository: CartsJpaRepository
): CartsRepository {

    override fun save(carts: Carts): Carts {
        return cartsJpaRepository.save(carts)
    }

    override fun findByUserId(userId: Long): List<Carts> {
        return cartsJpaRepository.findByUserId(userId)
    }

    override fun findByUserIdAndProductId(userId: Long, productId: Long): Carts? {
        return cartsJpaRepository.findByUserIdAndProductId(userId = userId, productId = productId)
    }

    override fun delete(carts: Carts) {
        cartsJpaRepository.delete(carts)
    }

}