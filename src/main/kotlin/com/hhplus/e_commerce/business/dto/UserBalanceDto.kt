package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Balance

data class UserBalanceDto(
    val userId: Long,
    val currentAmount: Int,
) {
    companion object {
        fun from(balance: Balance): UserBalanceDto {
            return UserBalanceDto(
                userId = balance.user.id,
                currentAmount = balance.amount,
            )
        }
    }
}
