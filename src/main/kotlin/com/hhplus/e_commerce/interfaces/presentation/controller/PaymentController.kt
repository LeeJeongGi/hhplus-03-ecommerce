package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.business.facade.PaymentFacade
import com.hhplus.e_commerce.interfaces.presentation.response.PaymentResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/payment")
class PaymentController(
    private val paymentFacade: PaymentFacade
) {

    /**
     * 주문 결제 API
     */
    @Operation(summary = "주문 결제", description = "사용자가 주문 결제를 요청합니다.")
    @ApiResponse(responseCode = "200", description = "주문 결제 성공")
    @ApiResponse(responseCode = "404", description = "주문 정보를 찾을 수 없음")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @PostMapping("/{orderId}")
    fun payment(@PathVariable orderId: Long): ResponseEntity<PaymentResponse> {
        val result = paymentFacade.processPayment(orderId)
        return ResponseEntity.ok(PaymentResponse.from(result))
    }
}