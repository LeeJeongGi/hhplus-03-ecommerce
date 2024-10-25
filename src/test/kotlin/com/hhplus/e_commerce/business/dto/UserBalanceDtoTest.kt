package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class UserBalanceDtoTest {
    
    @Test
    @DisplayName("유저의 잔액이 500원 있을 때 1000원을 사용하려는 요청이 오면 예외가 발생한다.")
    fun notEnoughMoney() {
        // given
        val userBalanceDto = UserBalanceDto(
            balanceId = 1L,
            userId = 1L,
            currentAmount = 500
        )

        // when
        val message = assertThrows<BusinessException> {
            userBalanceDto.isEnoughMoney(1000)
        }.message

        // then
        assertThat(message).isEqualTo(ErrorCode.User.INSUFFICIENT_BALANCE.message)
    }
}