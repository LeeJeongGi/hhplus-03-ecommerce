package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.CartsSaveDto
import com.hhplus.e_commerce.business.dto.CartsSaveResultDto
import com.hhplus.e_commerce.business.entity.Carts
import com.hhplus.e_commerce.business.repository.CartsRepository
import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartsService(
    private val cartsRepository: CartsRepository,
    private val productRepository: ProductRepository,
) {

    // 장바구니 항목 저장
    @Transactional
    fun save(cartsSaveDto: CartsSaveDto): CartsSaveResultDto {

        val product = productRepository.findById(cartsSaveDto.productId)
            ?: throw BusinessException.NotFound(ErrorCode.Product.NOT_FOUND_PRODUCT)

        val userCart = Carts(
            userId = cartsSaveDto.userId,
            productId = product.id,
        )
        val saveCarts =  cartsRepository.save(userCart)

        return CartsSaveResultDto.from(saveCarts)
    }

    fun findByUserId(userId: Long): List<CartsSaveResultDto> {
        val carts = cartsRepository.findByUserId(userId)

        return carts.map { CartsSaveResultDto.from(it) }
    }

    // 장바구니 항목 삭제
    @Transactional
    fun deleteByUserIdAndProductId(userId: Long, productId: Long): Carts {
        val cart = cartsRepository.findByUserIdAndProductId(userId, productId)
            ?: throw BusinessException.NotFound(ErrorCode.Carts.NOT_FOUND_CART)

        cartsRepository.delete(cart)

        return cart
    }


}