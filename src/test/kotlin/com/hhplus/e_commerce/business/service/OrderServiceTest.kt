package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.repository.OrderItemRepository
import com.hhplus.e_commerce.business.repository.OrderRepository
import com.hhplus.e_commerce.business.stub.OrderStub
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
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

    @Test
    @DisplayName("주문 정보 조회 실패 테스트 - 존재하지 않는 주문 ID로 조회 시 예외가 발생하는지 검증")
    fun findOrderByIdNotFoundTest() {
        // given
        val orderId = 999L  // 존재하지 않는 주문 ID
        every { orderRepository.findByOrderIdWithLock(orderId) } returns null

        // when & then
        val exception = assertThrows<BusinessException.NotFound> {
            orderService.findOrderById(orderId)
        }
        assertThat(exception.errorCode).isEqualTo(ErrorCode.Order.NOT_FOUND_ORDER)
    }

    @Test
    @DisplayName("주문 정보 조회 테스트 - 주문 ID를 통해 주문 정보를 정상적으로 조회하는지 검증하는 테스트")
    fun findOrderByIdTest() {
        // given
        val orderId = 1L
        val order = OrderStub.create(
            userId = 1L,
            totalAmount = 10000,
            status = "ORDERED"
        )

        every { orderRepository.findByOrderIdWithLock(orderId) } returns order

        // when
        val result = orderService.findOrderById(orderId)

        // then
        assertThat(result).isNotNull
        assertThat(result.userId).isEqualTo(order.userId)
        assertThat(result.totalAmount).isEqualTo(order.totalAmount)
        assertThat(result.status).isEqualTo(order.status)
    }

    @Test
    @DisplayName("주문 상태 업데이트 실패 테스트 - 존재하지 않는 주문 ID로 업데이트 시 예외가 발생하는지 검증")
    fun updateOrderStatusNotFoundTest() {
        // given
        val orderId = 999L  // 존재하지 않는 주문 ID
        val newStatus = "PAID"
        every { orderRepository.findByOrderIdWithLock(orderId) } returns null

        // when & then
        val exception = assertThrows<BusinessException.NotFound> {
            orderService.updateOrderStatus(orderId, newStatus)
        }
        assertThat(exception.errorCode).isEqualTo(ErrorCode.Order.NOT_FOUND_ORDER)
    }

    @Test
    @DisplayName("주문 상태 업데이트 테스트 - 주문 ID와 상태 값을 받아 주문 상태를 정상적으로 업데이트하는지 검증하는 테스트")
    fun updateOrderStatusTest() {
        // given
        val orderId = 1L
        val updatedStatus = "PAID"
        val order = OrderStub.create(
            userId = 1L,
            totalAmount = 10000,
            status = "ORDERED"
        )

        every { orderRepository.findByOrderIdWithLock(orderId) } returns order
        every { orderRepository.save(order) } returns order.apply { updateStatus(updatedStatus) }

        // when
        val result = orderService.updateOrderStatus(orderId, updatedStatus)

        // then
        assertThat(result).isNotNull
        assertThat(result.status).isEqualTo(updatedStatus)
    }
}