package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.dto.UserBalanceDto
import com.hhplus.e_commerce.business.entity.Balance
import com.hhplus.e_commerce.business.entity.User
import com.hhplus.e_commerce.business.repository.BalanceRepository
import com.hhplus.e_commerce.common.config.CacheConfig
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val balanceRepository: BalanceRepository,
) {

    fun updateCharge(balanceChargeDto: BalanceChargeDto, user: User): UserBalanceDto {

        val balance = balanceRepository.findByUserId(balanceChargeDto.userId)?.apply {
            updateAmount(balanceChargeDto.amount)
        } ?: balanceRepository.save(
            Balance(
                user = user,
                amount = balanceChargeDto.amount,
            )
        )

        return UserBalanceDto.from(balance)
    }

    @Cacheable(
        cacheNames = [CacheConfig.BALANCE_CACHE],
        key = "#userId",
        condition = "#userId != null",
        sync = true,
    )
    fun getUserBalance(userId: Long): UserBalanceDto {
        val userBalance =  balanceRepository.findByUserId(userId)
            ?: throw BusinessException.NotFound(ErrorCode.User.NOT_FOUND_USER)

        return UserBalanceDto.from(userBalance)
    }

    @CacheEvict(
        cacheNames = [CacheConfig.BALANCE_CACHE],
        key = "#userId",
    )
    fun changeBalance(userId: Long, totalAmount: Int): UserBalanceDto {
        val userBalance =  balanceRepository.findByUserIdWithLock(userId)
            ?: throw BusinessException.NotFound(ErrorCode.User.NOT_FOUND_USER)

        userBalance.updateAmount(-totalAmount)
        val paymentUserBalance = balanceRepository.save(userBalance)

        return UserBalanceDto.from(paymentUserBalance)
    }
}