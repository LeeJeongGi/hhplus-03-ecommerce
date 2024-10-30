package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.dto.UserBalanceDto
import com.hhplus.e_commerce.common.annotation.DistributedSimpleLock
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service

@Service
class BalanceSimpleLockFacade(
    private val balanceFacade: BalanceFacade,
) {

    @DistributedSimpleLock(
        key = "'userId:' + #{balanceChargeDto.userId}",
        waitTime = 5,
        leaseTime = 10,
    )
    fun charge(balanceChargeDto: BalanceChargeDto): UserBalanceDto {
        if (balanceChargeDto.amount < 0) {
            throw BusinessException.BadRequest(ErrorCode.Balance.BAD_REQUEST_BALANCE)
        }
        return balanceFacade.charge(balanceChargeDto)
    }
}