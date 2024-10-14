package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.repository.OrderItemRepository
import com.hhplus.e_commerce.business.repository.OrderRepository
import com.hhplus.e_commerce.business.stub.OrderStub
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class OrderServiceTest {

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var orderItemRepository: OrderItemRepository

    @InjectMockKs
    private lateinit var orderService: OrderService

    @Test
    @DisplayName("주문 오더 저장 테스트 - 유저, 총 금액, 주문한 상품 리스트 정보를 받아 정상적으로 저장하는지 검증 테스트")
    fun saveTest() {
        // given
        val orderSaveDto = OrderSaveDto(
            userId = 1L,
            totalAmount = 10000,
            listOf(
                ProductOrderDto(
                    productStockId = 1L,
                    quantity = 2
                ),
                ProductOrderDto(
                    productStockId = 2L,
                    quantity = 1
                ),
                ProductOrderDto(
                    productStockId = 3L,
                    quantity = 3
                )
            )
        )

        val order = OrderStub.create(
            userId = orderSaveDto.userId,
            totalAmount = orderSaveDto.totalAmount,
        )

        every { orderRepository.save(any()) } returns order
        every { orderItemRepository.saveAll(any()) } just Runs

        // when
        val result = orderService.save(orderSaveDto)

        // then
        assertThat(result).isNotNull
        assertThat(result.userId).isEqualTo(orderSaveDto.userId)
        assertThat(result.totalAmount).isEqualTo(orderSaveDto.totalAmount)
    }
}