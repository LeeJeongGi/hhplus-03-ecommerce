package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.dto.UserBalanceDto
import com.hhplus.e_commerce.business.entity.BalanceHistory
import com.hhplus.e_commerce.business.repository.BalanceHistoryRepository
import com.hhplus.e_commerce.business.repository.UserRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.orm.ObjectOptimisticLockingFailureException
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

        if (balanceChargeDto.amount < 0) {
            throw BusinessException.BadRequest(ErrorCode.Balance.BAD_REQUEST_BALANCE)
        }

        val user = userRepository.findById(balanceChargeDto.userId)
            ?: throw BusinessException.NotFound(ErrorCode.User.NOT_FOUND_USER)

        return kotlin.runCatching {
            val userBalanceDto = balanceService.updateCharge(balanceChargeDto, user)

            val balanceHistory = BalanceHistory(
                balanceId = userBalanceDto.balanceId,
                changeAmount = balanceChargeDto.amount,
                changeType = "SAVE",
                balanceAfter = userBalanceDto.currentAmount,
                description = "잔액 충전"
            )
            balanceHistoryRepository.save(balanceHistory)
            userBalanceDto
        }.getOrElse { exception ->
            when (exception) {
                is ObjectOptimisticLockingFailureException, is DataIntegrityViolationException ->
                    throw BusinessException.BadRequest(ErrorCode.Balance.ALREADY_CHARGED)
                else -> throw exception
            }
        }
    }
}