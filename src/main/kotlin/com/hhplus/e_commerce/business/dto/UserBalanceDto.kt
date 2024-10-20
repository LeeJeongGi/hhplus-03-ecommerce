package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Balance
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException

data class UserBalanceDto(
    val balanceId: Long,
    val userId: Long,
    val currentAmount: Int,
) {
    fun isEnoughMoney(orderAmount: Int) {
        if (this.currentAmount < orderAmount) {
            BusinessException.BadRequest(ErrorCode.User.INSUFFICIENT_BALANCE)
        }
    }

    companion object {
        fun from(balance: Balance): UserBalanceDto {
            return UserBalanceDto(
                balanceId = balance.id,
                userId = balance.user.id,
                currentAmount = balance.amount,
            )
        }
    }
}
