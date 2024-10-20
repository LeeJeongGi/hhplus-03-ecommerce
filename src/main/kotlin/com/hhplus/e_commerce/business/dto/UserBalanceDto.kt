package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Balance

data class UserBalanceDto(
    val balanceId: Long,
    val userId: Long,
    val currentAmount: Int,
) {
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
