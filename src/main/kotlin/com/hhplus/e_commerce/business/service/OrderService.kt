package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.OrderDto
import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.dto.OrderSaveResultDto
import com.hhplus.e_commerce.business.entity.Order
import com.hhplus.e_commerce.business.entity.OrderItem
import com.hhplus.e_commerce.business.repository.OrderItemRepository
import com.hhplus.e_commerce.business.repository.OrderRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {

    fun save(orderSaveDto: OrderSaveDto): OrderSaveResultDto {

        val order = Order(
            userId = orderSaveDto.userId,
            status = "ORDERED",
            totalAmount = orderSaveDto.totalAmount,
            orderDate = LocalDateTime.now(),
        )
        val saveOrder = orderRepository.save(order)

        val orderItems = orderSaveDto.products.map { productOrderDto ->
            OrderItem(
                orderId = saveOrder.id,
                productStockId = productOrderDto.productStockId,
                quantity = productOrderDto.quantity,
            )
        }
        orderItemRepository.saveAll(orderItems)

        return OrderSaveResultDto.from(saveOrder)
    }

    fun findOrderById(orderId: Long): OrderDto {
        val order = orderRepository.findByOrderIdWithLock(orderId)
            ?: throw BusinessException.NotFound(ErrorCode.Order.NOT_FOUND_ORDER)

        return OrderDto.from(order)
    }

    fun updateOrderStatus(orderId: Long, status: String): OrderDto {
        val order = orderRepository.findByOrderIdWithLock(orderId)
            ?: throw BusinessException.NotFound(ErrorCode.Order.NOT_FOUND_ORDER)

        order.updateStatus(status)
        val saveOrder = orderRepository.save(order)

        return OrderDto.from(saveOrder)
    }


}