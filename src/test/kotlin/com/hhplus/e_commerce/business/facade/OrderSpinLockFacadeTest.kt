package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.entity.ProductStock
import com.hhplus.e_commerce.business.repository.*
import com.hhplus.e_commerce.business.stub.BalanceStub
import com.hhplus.e_commerce.business.stub.ProductStub
import com.hhplus.e_commerce.business.stub.UserStub
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test

@SpringBootTest
class OrderSpinLockFacadeTest {

    @Autowired
    lateinit var orderSpinLockFacade: OrderSpinLockFacade

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var balanceRepository: BalanceRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var productStockRepository: ProductStockRepository

    @Autowired
    lateinit var orderItemRepository: OrderItemRepository

    @Test
    @DisplayName("주문 동시성 통합 테스트 - 재고가 50개 있을 때 100번 요청 후 성공 50, 실패 50 검증 테스트")
    fun saveIntegrationTest() {

        val user = UserStub.create("lee")
        val saveUser = userRepository.save(user)

        val balance = BalanceStub.create(user = saveUser, amount = 1000000)
        val userBalance = balanceRepository.save(balance)

        val product = ProductStub.create("aidas", "SHOES", 1)
        val saveProduct = productRepository.save(product)

        val productStock = ProductStock(
            productId = saveProduct.id,
            size = "270",
            quantity = 50,
        )
        val saveProductStock = productStockRepository.save(productStock)

        val executor = Executors.newFixedThreadPool(100)
        val lectureLatch = CountDownLatch(100)
        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        // when && then
        try {
            repeat(100) {
                executor.submit {
                    try {
                        val orderSaveDto = OrderSaveDto(
                            userId = saveUser.id,
                            totalAmount = 1,
                            products = listOf(
                                ProductOrderDto(
                                    productStockId = saveProductStock.id!!,
                                    quantity = 1 // 각 주문에 1씩 요청
                                )
                            )
                        )

                        val saveResult = orderSpinLockFacade.save(orderSaveDto)
                        successCount.incrementAndGet()
                    } catch (ex: BusinessException) {
                        failCount.incrementAndGet()
                    } finally {
                        lectureLatch.countDown()
                    }
                }
            }
            lectureLatch.await()

            assertThat(successCount.get()).isEqualTo(50)

            val successResults = orderItemRepository.findByProductStockId(saveProductStock.id!!)
            assertThat(successResults.size).isEqualTo(50)
            assertThat(failCount.get()).isEqualTo(50)

        } finally {
            executor.shutdown()
        }

    }
}