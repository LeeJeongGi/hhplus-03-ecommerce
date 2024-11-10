package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.repository.BalanceRepository
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
import kotlin.system.measureTimeMillis
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class BalanceServiceTest {

    @MockK
    lateinit var balanceRepository: BalanceRepository

    @InjectMockKs
    private lateinit var balanceService: BalanceService

    @Test
    @DisplayName("잔액 차감 테스트 : 잔액이 1000원 있을 때 500원 상품을 구입하면 잔액이 500원 남아 있다.")
    fun updateChargeOrderUser() {
        // given
        val userId = 0L
        val initialAmount = 1000
        val rechargeAmount = -500

        val saveUser = UserStub.create("Lee")
        val balance = BalanceStub.create(saveUser, amount = initialAmount)

        val balanceChargeDto = BalanceChargeDto(
            userId = userId,
            amount = rechargeAmount,
        )

        every { balanceRepository.findByUserId(userId) } returns balance

        // when
        val updateBalance = balanceService.updateCharge(balanceChargeDto, saveUser)

        // then
        assertThat(updateBalance.userId).isEqualTo(balanceChargeDto.userId)
        assertThat(updateBalance.currentAmount).isEqualTo(initialAmount + rechargeAmount)
    }

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

        every { balanceRepository.findByUserId(userId) } returns balance

        // when
        val updateBalance = balanceService.updateCharge(balanceChargeDto, saveUser)

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
        every { balanceRepository.findByUserId(userId) } returns null

        val newBalance = BalanceStub.create(saveUser, rechargeAmount)
        every { balanceRepository.save(any()) } returns newBalance

        // when
        val updateBalance = balanceService.updateCharge(balanceChargeDto, saveUser)

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

    @Test
    @DisplayName("유저 잔액 변경 성공 테스트 - 유저의 잔액이 1000원 있을 때 500원 상품을 산 후 남은 잔액이 500원인지 검증하는 테스트")
    fun changeBalanceTest() {
        // given
        val userId = 1L
        val user = UserStub.create("User")
        val balance = BalanceStub.create(user, 1000)

        every { balanceRepository.findByUserIdWithLock(userId) } returns balance
        every { balanceRepository.save(balance) } returns balance  // save 메서드 모킹 추가

        // when
        val userBalance = balanceService.changeBalance(userId, 500)

        // then
        assertThat(userBalance.currentAmount).isEqualTo(balance.amount)
    }

    @Test
    @DisplayName("캐시 적용 여부에 따른 유저 잔액 조회 성능 테스트")
    fun getUserBalancePerformanceTest() {
        // given
        val userId = 1L
        val user = UserStub.create("User")
        val balance = BalanceStub.create(user, 1000)
        every { balanceRepository.findByUserId(userId) } returns balance

        // 캐시가 없는 상태에서 성능 측정
        val timeWithoutCache = measureTimeMillis {
            repeat(100000) { balanceService.getUserBalance(userId) }
        }

        // 캐시가 있는 상태에서 성능 측정
        balanceService.getUserBalance(userId) // 캐시 저장
        val timeWithCache = measureTimeMillis {
            repeat(100000) { balanceService.getUserBalance(userId) }
        }

        println("캐시 미적용 상태 조회 시간: ${timeWithoutCache}ms")
        println("캐시 적용 상태 조회 시간: ${timeWithCache}ms")

        assertThat(timeWithCache).isLessThan(timeWithoutCache)
    }
}