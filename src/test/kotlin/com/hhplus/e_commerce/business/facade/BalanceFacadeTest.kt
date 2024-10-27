package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.dto.UserBalanceDto
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
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
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
    @DisplayName("잔액 충전 동시성 테스트 - 동시에 500원 충전이 10번 들어와도 충전은 한번만 완료 되어야 한다.")
    fun chargeIntegrationTest() {

        // given
        val user = UserStub.create("Lee")
        val saveUser = userRepository.save(user)

        val balance = BalanceStub.create(saveUser, 500)
        balanceRepository.save(balance)

        val executor = Executors.newFixedThreadPool(100)
        val latch = CountDownLatch(100)
        val results = Collections.synchronizedList(mutableListOf<Result<UserBalanceDto>>())

        // when
        try {
            repeat(100) {
                executor.submit {
                    try {
                        val balanceChargeDto = BalanceChargeDto(
                            userId = saveUser.id,
                            amount = 500
                        )

                        val result = kotlin.runCatching {
                            balanceFacade.charge(balanceChargeDto)
                        }
                        results.add(result)
                    } finally {
                        latch.countDown()
                    }
                }
            }
        } finally {
            executor.shutdown()
        }
        latch.await()

        // then
        val successCount = results.count{ it.isSuccess }
        val failCount = results.count{ it.isFailure }

        assertThat(successCount).isEqualTo(1)
        assertThat(failCount).isEqualTo(99)

        val resultUserBalance = balanceService.getUserBalance(saveUser.id)
        assertThat(resultUserBalance.currentAmount).isEqualTo(1000)

    }
}