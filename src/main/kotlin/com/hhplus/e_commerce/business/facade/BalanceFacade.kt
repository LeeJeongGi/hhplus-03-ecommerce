package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.dto.UserBalanceDto
import com.hhplus.e_commerce.business.entity.BalanceHistory
import com.hhplus.e_commerce.business.repository.BalanceHistoryRepository
import com.hhplus.e_commerce.business.repository.UserRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BalanceFacade(
    private val userRepository: UserRepository,
    private val balanceService: BalanceService,
    private val balanceHistoryRepository: BalanceHistoryRepository,
) {

    @Transactional
    fun charge(balanceChargeDto: BalanceChargeDto): UserBalanceDto {

        val user = userRepository.findById(balanceChargeDto.userId)
            ?: throw BusinessException.NotFound(ErrorCode.User.NOT_FOUND_USER)

        val userBalanceDto = balanceService.updateCharge(balanceChargeDto, user)

        val balanceHistory = BalanceHistory(
            balanceId = userBalanceDto.balanceId,
            changeAmount = balanceChargeDto.amount,
            changeType = "SAVE",
            balanceAfter = userBalanceDto.currentAmount,
            description = "잔액 충전"
        )
        balanceHistoryRepository.save(balanceHistory)

        return userBalanceDto
    }
}