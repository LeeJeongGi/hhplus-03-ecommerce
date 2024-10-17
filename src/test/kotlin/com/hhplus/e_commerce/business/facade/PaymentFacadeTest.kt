package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.entity.ProductStock
import com.hhplus.e_commerce.business.repository.*
import com.hhplus.e_commerce.business.stub.BalanceStub
import com.hhplus.e_commerce.business.stub.ProductStub
import com.hhplus.e_commerce.business.stub.UserStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.BeforeTest
import kotlin.test.Test

@SpringBootTest
class PaymentFacadeTest {

    @Autowired
    lateinit var paymentFacade: PaymentFacade

    @Autowired
    lateinit var orderFacade: OrderFacade

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var balanceRepository: BalanceRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var productStockRepository: ProductStockRepository

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    @BeforeTest
    fun setUp() {
        orderRepository.deleteAll()
    }

    @Test
    @DisplayName("주문 완료 테스트 - 외부 API 결제 정보 전달이 실패하더라고 결제 정보는 저장되는지 검증 테스트 ")
    fun paymentMustSaveInfoTest() {
        // given
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

        val saveResult = orderFacade.saveOrder(orderSaveDto)

        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))
        System.setOut(originalOut)

        // when
        val paymentResult = paymentFacade.processPayment(saveResult.orderId)
        val savePaymentResult = paymentRepository.findByOrderId(saveResult.orderId)

        // then
        assertThat(saveResult.orderId).isEqualTo(paymentResult.orderId)
        assertThat(savePaymentResult).isNotNull
        assertThat(savePaymentResult?.status).isEqualTo("COMPLETED")
    }

}