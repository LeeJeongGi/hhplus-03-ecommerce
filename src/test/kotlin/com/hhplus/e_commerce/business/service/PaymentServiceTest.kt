package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.PaymentSaveDto
import com.hhplus.e_commerce.business.repository.PaymentRepository
import com.hhplus.e_commerce.business.stub.PaymentStub
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class PaymentServiceTest {

    @MockK
    private lateinit var paymentRepository: PaymentRepository

    @InjectMockKs
    private lateinit var paymentService: PaymentService

    @Test
    @DisplayName("결제 정보 저장 테스트 - PaymentSaveDto로 결제 정보 저장이 성공하는지 검증")
    fun savePaymentTest() {
        // given
        val paymentSaveDto = PaymentSaveDto(
            userId = 1L,
            orderId = 2L,
            status = "COMPLETED",
            amount = 1000,
        )

        val payment = PaymentStub.create(
            userId = paymentSaveDto.userId,
            orderId = paymentSaveDto.orderId,
            amount = paymentSaveDto.amount,
            status = "COMPLETED",
            paymentDate = LocalDateTime.now()
        )

        every { paymentRepository.save(any()) } returns payment

        // when
        val result = paymentService.save(paymentSaveDto)

        // then
        assertThat(result).isNotNull
        assertThat(result.userId).isEqualTo(payment.userId)
        assertThat(result.orderId).isEqualTo(payment.orderId)
        assertThat(result.amount).isEqualTo(payment.amount)
        assertThat(result.status).isEqualTo(payment.status)
    }
}