package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.repository.ProductStockRepository
import com.hhplus.e_commerce.business.stub.ProductStockStub
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ProductStockServiceTest {

    @MockK
    private lateinit var productStockRepository: ProductStockRepository

    @InjectMockKs
    private lateinit var productStockService: ProductStockService

    @Test
    @DisplayName("주문 상품 검증 성공 테스트 - 주문한 상품이 존재하고 수량이 존재하는지 검증하는 테스트")
    fun validTest() {
        // given
        val productOrders = listOf(
            ProductOrderDto(productStockId = 1L, quantity = 2),
            ProductOrderDto(productStockId = 2L, quantity = 1)
        )

        val productStock1 = ProductStockStub.create(productId = 1L, size = "M", quantity = 10)
        val productStock2 = ProductStockStub.create(productId = 2L, size = "L", quantity = 5)

        every { productStockRepository.findExistingIds(listOf(1L, 2L)) } returns listOf(1L, 2L)
        every { productStockRepository.findByIdsWithLock(listOf(1L, 2L)) } returns listOf(productStock1, productStock2)

        // when
        val result = productStockService.valid(productOrders)

        // then
        assertThat(result.size).isEqualTo(productOrders.size)
        assertThat(result[0].productStockId).isEqualTo(productOrders[0].productStockId)
        assertThat(result[0].quantity).isEqualTo(productOrders[0].quantity)
        assertThat(result[1].productStockId).isEqualTo(productOrders[1].productStockId)
        assertThat(result[1].quantity).isEqualTo(productOrders[1].quantity)
    }

    @Test
    @DisplayName("주문 상품 검증 실패 테스트 - 상품이 존재하지 않는 경우 예외가 발생하는지 검증하는 테스트")
    fun validProductNotFound() {
        // given
        val productOrders = listOf(
            ProductOrderDto(productStockId = 1L, quantity = 5)
        )

        every { productStockRepository.findExistingIds(listOf(1L)) } returns emptyList()
        every { productStockRepository.findByIdsWithLock(listOf(1L)) } returns emptyList()

        // when & then
        val message = assertThrows<BusinessException.NotFound> {
            productStockService.valid(productOrders)
        }.message

        assertThat(message).isEqualTo(ErrorCode.Product.NOT_FOUND_PRODUCT.message)
    }

    @Test
    @DisplayName("주문 상품 검증 실패 테스트 - 상품의 수량이 적을 때 생기는 예외 처리 테스트")
    fun throwsWhenStockInsufficient() {
        // given
        val productOrders = listOf(
            ProductOrderDto(productStockId = 1L, quantity = 5)
        )

        val productStock = ProductStockStub.create(productId = 1L, size = "M", quantity = 1)

        every { productStockRepository.findExistingIds(listOf(1L)) } returns listOf(1L)
        every { productStockRepository.findByIdsWithLock(listOf(1L)) } returns listOf(productStock)

        // when & then
        val message = assertThrows<BusinessException.BadRequest> {
            productStockService.valid(productOrders)
        }.message

        assertThat(message).isEqualTo(ErrorCode.Product.OUT_OF_STOCK.message)
    }

    @Test
    @DisplayName("재고 업데이트 성공 테스트 - 주문 수량만큼 재고가 정상적으로 차감되는지 검증")
    fun updateStockSuccess() {
        // given
        val productOrders = listOf(
            ProductOrderDto(productStockId = 1L, quantity = 3),
            ProductOrderDto(productStockId = 2L, quantity = 1)
        )

        val productStock1 = ProductStockStub.create(productId = 1L, quantity = 10)
        val productStock2 = ProductStockStub.create(productId = 2L, quantity = 5)

        every { productStockRepository.findById(1L) } returns productStock1
        every { productStockRepository.findById(2L) } returns productStock2
        every { productStockRepository.saveAll(any()) } just runs

        // when
        val result = productStockService.updateStock(productOrders)

        // then
        assertThat(productStock1.quantity).isEqualTo(7) // 10 - 3
        assertThat(productStock2.quantity).isEqualTo(4) // 5 - 1
        assertThat(result).isEqualTo(productOrders)
    }

    @Test
    @DisplayName("재고 업데이트 실패 테스트 - 재고가 없을 경우 예외가 발생하는지 검증")
    fun updateStockThrowsWhenNotFound() {
        // given
        val productOrders = listOf(
            ProductOrderDto(productStockId = 1L, quantity = 3)
        )

        every { productStockRepository.findById(1L) } returns null

        // when & then
        val exception = assertThrows<BusinessException.NotFound> {
            productStockService.updateStock(productOrders)
        }

        assertThat(exception.errorCode).isEqualTo(ErrorCode.Product.NOT_FOUND_PRODUCT)
    }

}