package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.repository.BalanceRepository
import com.hhplus.e_commerce.business.repository.UserRepository
import com.hhplus.e_commerce.business.stub.BalanceStub
import com.hhplus.e_commerce.business.stub.UserStub
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class BalanceServiceTest {

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var balanceRepository: BalanceRepository

    @InjectMockKs
    private lateinit var balanceService: BalanceService

    @Test
    @DisplayName("잔액 충전 테스트 : 잔액이 있는 유저라면 기존 잔액 + 충전 잔액으로 업데이트 한다.")
    fun updateChargeOldUser() {
        // given
        val userId = 0L
        val initialAmount = 1000
        val rechargeAmount = 500

        val saveUser = UserStub.create("Lee")
        val balance = BalanceStub.create(saveUser, amount = initialAmount)

        val balanceChargeDto = BalanceChargeDto(
            userId = userId,
            amount = rechargeAmount,
        )

        every { userRepository.findById(userId) } returns saveUser
        every { balanceRepository.findByUserIdWithLock(userId) } returns balance

        // when
        val updateBalance = balanceService.updateCharge(balanceChargeDto)

        // then
        assertThat(updateBalance.userId).isEqualTo(balanceChargeDto.userId)
        assertThat(updateBalance.currentAmount).isEqualTo(initialAmount + rechargeAmount)
    }

    @Test
    @DisplayName("잔액 충전 테스트 : 잔액 이력이 없는 신규 유저라면 신규 유저의 잔액은 +충전 잔액만틈 추가 한다.")
    fun updateChargeNewUser() {
        // given
        val userId = 0L
        val rechargeAmount = 500

        val saveUser = UserStub.create("Lee")

        val balanceChargeDto = BalanceChargeDto(
            userId = userId,
            amount = rechargeAmount,
        )
        every { userRepository.findById(userId) } returns saveUser
        every { balanceRepository.findByUserIdWithLock(userId) } returns null

        val newBalance = BalanceStub.create(saveUser, rechargeAmount)
        every { balanceRepository.save(any()) } returns newBalance

        // when
        val updateBalance = balanceService.updateCharge(balanceChargeDto)

        // then
        assertThat(updateBalance.userId).isEqualTo(balanceChargeDto.userId)
        assertThat(updateBalance.currentAmount).isEqualTo(rechargeAmount)
    }

    @Test
    @DisplayName("유저 잔액 조회 실패 테스트 - 포인트 충전 이력이 없는 유저에 대해 포인트 조회시 실패 발생하는 케이스")
    fun getUserException1() {
        // given
        every { balanceRepository.findByUserId(1L) } returns null

        // when
        val message = assertThrows<BusinessException.NotFound> {
            balanceService.getUserBalance(1L)
        }.message

        // then
        assertThat(message).isEqualTo(ErrorCode.User.NOT_FOUND_USER.message)
    }
}