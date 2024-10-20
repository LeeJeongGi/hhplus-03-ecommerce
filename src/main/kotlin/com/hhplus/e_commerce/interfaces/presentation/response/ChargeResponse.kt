package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.business.dto.UserBalanceDto

data class ChargeResponse(
    val userId: Long,
    val balance: Int,
) {
    companion object {
        fun from(userBalance: UserBalanceDto): ChargeResponse {
            return ChargeResponse(
                userId = userBalance.userId,
                balance = userBalance.currentAmount,
            )
        }
    }
}
