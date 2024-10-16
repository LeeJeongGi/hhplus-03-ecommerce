package com.hhplus.e_commerce.business.dto

data class BalanceChargeDto(
    val userId: Long,
    val amount: Int,
) {
    companion object {
        fun of(userId: Long, amount: Int): BalanceChargeDto {
            return BalanceChargeDto(
                userId = userId,
                amount = amount,
            )
        }
    }
}
