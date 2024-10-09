package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.common.ErrorResponse
import com.hhplus.e_commerce.interfaces.presentation.request.ChargeRequest
import com.hhplus.e_commerce.interfaces.presentation.response.ChargeResponse
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class BalanceController {

    /**
     * 1. 잔액 충전 API
     */
    @PostMapping("/{userId}/balance")
    fun chargeBalance(
        @PathVariable userId: Long,
        @RequestBody request: ChargeRequest
    ): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(
                ChargeResponse(
                    userId = 1L,
                    balance = 1000,
                )
            )
        } catch (e: BadRequestException) {
            return ResponseEntity(e.message?.let { ErrorResponse(code = 400, message = it) }, HttpStatus.BAD_REQUEST)
        }
    }

    /**
     * 2. 잔액 조회 API
     */
    @GetMapping("/{userId}/balance")
    fun getBalance(
        @PathVariable userId: Long
    ): ResponseEntity<Any> {
        return try {
            // 잔액 조회 로직 구현
            val response = ChargeResponse(
                userId = 1L,
                balance = 2000 // 예시로 설정
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity(e.message?.let { ErrorResponse(code = 400, message = it) }, HttpStatus.NOT_FOUND)
        }
    }
}