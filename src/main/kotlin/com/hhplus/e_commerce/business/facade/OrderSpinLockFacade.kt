package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.facade.dto.OrderSaveResultDto
import com.hhplus.e_commerce.common.annotation.DistributedSimpleLock
import org.springframework.stereotype.Service

@Service
class OrderSpinLockFacade(
    private val orderFacade: OrderFacade
) {

    @DistributedSimpleLock(
        key = "'userId:' + #{orderSaveDto.userId} + " +
                "':products:' + #{orderSaveDto.products.![productId].toString()}",
        waitTime = 10,
        leaseTime = 20,
    )
    fun save(orderSaveDto: OrderSaveDto): OrderSaveResultDto {
        println("Distributed Lock Key: userId:${orderSaveDto.userId}:productStockId:${orderSaveDto.products[0].productStockId}")
        return orderFacade.saveOrder(orderSaveDto)
    }
}