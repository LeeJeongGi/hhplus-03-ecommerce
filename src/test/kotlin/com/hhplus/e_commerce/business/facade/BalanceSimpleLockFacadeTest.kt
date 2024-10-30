package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.repository.BalanceRepository
import com.hhplus.e_commerce.business.repository.UserRepository
import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.stub.BalanceStub
import com.hhplus.e_commerce.business.stub.UserStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test

@SpringBootTest
class BalanceSimpleLockFacadeTest {

    @Autowired
    private lateinit var balanceSimpleLockFacade: BalanceSimpleLockFacade

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
    @DisplayName("잔액 충전 동시성 테스트 - 동시에 500원 충전이 1000번 들어와도 충전은 한번만 완료 되어야 한다.")
    fun chargeIntegrationTest() {

        // given
        val startTime = System.nanoTime()
        val user = UserStub.create("Lee")
        val saveUser = userRepository.save(user)

        val balance = BalanceStub.create(saveUser, 500)
        balanceRepository.save(balance)

        val executor = Executors.newFixedThreadPool(1000)
        val latch = CountDownLatch(1000)
        val successCount = AtomicInteger()
        val failCount = AtomicInteger()

        // when
        try {
            repeat(1000) {
                executor.submit {
                    try {
                        val balanceChargeDto = BalanceChargeDto(
                            userId = saveUser.id,
                            amount = 500
                        )
                        balanceSimpleLockFacade.charge(balanceChargeDto)
                        successCount.incrementAndGet()
                    } catch (e: Exception) {
                        failCount.incrementAndGet()
                    } finally {
                        latch.countDown()
                    }
                }
            }
        } finally {
            executor.shutdown()
        }
        latch.await()
        val endTime = System.nanoTime()
        val duration = Duration.ofNanos(endTime - startTime)

        // then
        assertThat(successCount.get()).isEqualTo(1)
        assertThat(failCount.get()).isEqualTo(999)

        val resultUserBalance = balanceService.getUserBalance(saveUser.id)
        assertThat(resultUserBalance.currentAmount).isEqualTo(1000)

        println("테스트 실행 시간: ${duration.toMillis()} 밀리초")
        println("테스트 실행 시간: ${duration.toMillis()} 밀리초")
        println("성공한 충전 횟수: $successCount")
        println("실패한 충전 횟수: $failCount")
        println("최종 잔액: ${resultUserBalance.currentAmount}")
    }
}