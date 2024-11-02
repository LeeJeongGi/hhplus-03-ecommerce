package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.facade.dto.OrderSaveResultDto
import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.service.OrderService
import com.hhplus.e_commerce.business.service.ProductStockService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderFacade(
    private val productStockService: ProductStockService,
    private val balanceService: BalanceService,
    private val orderService: OrderService,
) {

    @Transactional
    fun saveOrder(orderSaveDto: OrderSaveDto): OrderSaveResultDto {

        // 상품 존재 하는지 조회
        val orderProducts = productStockService.valid(orderSaveDto.products)

        // 유저 조회 - 유저 잔액 검증
        val userBalance = balanceService.getUserBalance(orderSaveDto.userId)
        userBalance.isEnoughMoney(orderSaveDto.totalAmount)

        // 주문 정보 저장
        val result = orderService.save(orderSaveDto)

        // 재고 정보 업데이트
        productStockService.updateStock(orderSaveDto.products)

        return result
    }
}