package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.PaymentSaveDto
import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import com.hhplus.e_commerce.business.entity.Payment
import com.hhplus.e_commerce.business.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {

    fun save(paymentSaveDto: PaymentSaveDto): PaymentSaveResultDto {
        val payment = paymentRepository.save(Payment.from(paymentSaveDto))

        return PaymentSaveResultDto.from(payment)
    }
}