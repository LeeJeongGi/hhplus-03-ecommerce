package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.facade.dto.OrderSaveResultDto
import com.hhplus.e_commerce.common.annotation.DistributedRLock
import org.springframework.stereotype.Service

@Service
class OrderRLockFacade(
    private val orderFacade: OrderFacade
) {

    @DistributedRLock(
        key = "'userId:' + #{balanceChargeDto.userId}",
        waitTime = 5,
        leaseTime = 10,
    )
    fun saveOrder(orderSaveDto: OrderSaveDto): OrderSaveResultDto {
        return orderFacade.saveOrder(orderSaveDto)
    }
}