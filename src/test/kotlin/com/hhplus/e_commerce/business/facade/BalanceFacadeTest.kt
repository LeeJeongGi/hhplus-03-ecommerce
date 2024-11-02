package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.repository.BalanceRepository
import com.hhplus.e_commerce.business.repository.UserRepository
import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.stub.BalanceStub
import com.hhplus.e_commerce.business.stub.UserStub
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@SpringBootTest
class BalanceFacadeTest {

    @Autowired
    private lateinit var balanceFacade: BalanceFacade

    @Autowired
    private lateinit var balanceService: BalanceService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var balanceRepository: BalanceRepository

    @BeforeEach
    fun setUp() {
        balanceRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("잔액 충전 성공 테스트 - 기존 500원의 잔액을 가지고 있는 유저가 1000원을 추가로 충전했을 때 잔액이 1500이 되는지 검증 테스트")
    fun chargeTest() {
        // given
        val user = UserStub.create("Lee")
        val saveUser = userRepository.save(user)

        val balance = BalanceStub.create(saveUser, 500)
        val saveUserBalance = balanceRepository.save(balance)

        val balanceChargeDto = BalanceChargeDto(
            userId = saveUser.id,
            amount = 1000
        )

        // when
        val updateUserBalance = balanceFacade.charge(balanceChargeDto)

        // then
        assertThat(updateUserBalance.userId).isEqualTo(saveUser.id)
        assertThat(updateUserBalance.currentAmount).isEqualTo(saveUserBalance.amount + balanceChargeDto.amount)
    }

    @Test
    @DisplayName("유저 잔액 조회 성공 테스트 - 500원 잔액을 가진 유저를 조회 했을 때 정상적으로 반환하는지 확인 테스트")
    fun getUserChargeException1() {
        // given
        val user = UserStub.create("Lee")
        val saveUser = userRepository.save(user)

        val balance = BalanceStub.create(saveUser, 500)
        balanceRepository.save(balance)

        // when
        val userBalance = balanceService.getUserBalance(saveUser.id)

        // then
        assertThat(userBalance.userId).isEqualTo(saveUser.id)
        assertThat(userBalance.currentAmount).isEqualTo(balance.amount)
    }
}