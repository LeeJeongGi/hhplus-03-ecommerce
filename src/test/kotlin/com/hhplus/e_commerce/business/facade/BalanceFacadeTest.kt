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
    @DisplayName("잔액 충전 실패 테스트 - 잔액이 0원 보다 작은 요청에 대해 실패하는 테스트 케이스 작성")
    fun chargeException1() {
        // given
        val balanceChargeDto = BalanceChargeDto(
            userId = 1L,
            amount = -1,
        )

        // when
        val message = assertThrows<BusinessException.BadRequest> {
            balanceFacade.charge(balanceChargeDto)
        }.message

        // then
        assertThat(message).isEqualTo("잘못된 잔액 충전 요청입니다.")
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

    @Test
    @DisplayName("잔액 충전 동시성 테스트 - 동시에 500, 600원에 대한 충전이 들어올 경우 순차적으로 처리되어 1100원이 증가해야 한다.")
    fun chargeIntegrationTest() {

        // given
        val user = UserStub.create("Lee")
        val saveUser = userRepository.save(user)

        val balance = BalanceStub.create(saveUser, 500)
        balanceRepository.save(balance)

        val balanceChargeDto1 = BalanceChargeDto(
            userId = saveUser.id,
            amount = 500
        )

        val balanceChargeDto2 = BalanceChargeDto(
            userId = saveUser.id,
            amount = 600
        )

        val executor = Executors.newFixedThreadPool(2)
        val thread1 = CompletableFuture.supplyAsync({
            balanceFacade.charge(balanceChargeDto1)
        }, executor)

        val thread2 = CompletableFuture.supplyAsync({
            balanceFacade.charge(balanceChargeDto2)
        }, executor)

        // when
        CompletableFuture.allOf(thread1, thread2).join()
        val result = balanceService.getUserBalance(saveUser.id)

        // then
        assertThat(result.userId).isEqualTo(saveUser.id)
        assertThat(result.currentAmount).isEqualTo(balance.amount + balanceChargeDto1.amount + balanceChargeDto2.amount)

        // 실행 중인 스레드 풀 종료
        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)
    }
}