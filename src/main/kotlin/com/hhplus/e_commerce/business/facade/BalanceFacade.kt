package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.dto.UserBalanceDto
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BalanceFacade(
    private val balanceService: BalanceService,
) {

    @Transactional
    fun charge(balanceChargeDto: BalanceChargeDto): UserBalanceDto {

        if (balanceChargeDto.amount < 0) {
            throw BusinessException.BadRequest(ErrorCode.Balance.BAD_REQUEST_BALANCE)
        }

        return balanceService.updateCharge(balanceChargeDto)
    }

    fun getUserBalance(userId: Long): UserBalanceDto {
        return balanceService.getUserBalance(userId = userId)
    }
}