package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.repository.CartsRepository
import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.business.stub.CartsStub
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class CartsServiceTest {

    @MockK
    private lateinit var cartsRepository: CartsRepository

    @MockK
    private lateinit var productRepository: ProductRepository

    @InjectMockKs
    private lateinit var cartsService: CartsService

    @Test
    @DisplayName("장바구니 항목 삭제 테스트 - 성공적으로 userId와 productId로 장바구니 항목을 삭제")
    fun deleteByUserIdAndProductIdSuccessTest() {
        // given
        val userId = 1L
        val productId = 2L
        val cart = CartsStub.create(userId = userId, productId = productId)

        every { cartsRepository.findByUserIdAndProductId(userId, productId) } returns cart
        every { cartsRepository.delete(cart) } returns Unit

        // when
        cartsService.deleteByUserIdAndProductId(userId, productId)

        // then
        verify(exactly = 1) { cartsRepository.delete(cart) }
    }

    @Test
    @DisplayName("장바구니 항목 삭제 테스트 - userId와 productId로 장바구니 항목이 존재하지 않을 때 예외 발생")
    fun deleteByUserIdAndProductIdNotFoundTest() {
        // given
        val userId = 1L
        val productId = 2L

        every { cartsRepository.findByUserIdAndProductId(userId, productId) } returns null

        // when & then
        val exception = assertThrows<BusinessException.NotFound> {
            cartsService.deleteByUserIdAndProductId(userId, productId)
        }
        assertThat(exception.errorCode).isEqualTo(ErrorCode.Carts.NOT_FOUND_CART)

    }
}