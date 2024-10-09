package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.common.error.response.ErrorResponse
import com.hhplus.e_commerce.common.error.exception.BusinessException
import com.hhplus.e_commerce.interfaces.presentation.request.ChargeRequest
import com.hhplus.e_commerce.interfaces.presentation.response.ChargeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class BalanceController {

    /**
     * 1. 잔액 충전 API
     */
    @Operation(summary = "잔액 충전", description = "사용자의 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "충전 성공")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @PostMapping("/{userId}/balance")
    fun chargeBalance(
        @PathVariable userId: Long,
        @RequestBody request: ChargeRequest
    ): ResponseEntity<Any> {
        return try {
            return ResponseEntity.ok(
                ChargeResponse(
                    userId = 1L,
                    balance = 1000,
                )
            )
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message), HttpStatus.NOT_FOUND)
        }
    }

    /**
     * 2. 잔액 조회 API
     */
    @Operation(summary = "잔액 조회", description = "사용자의 현재 잔액을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "잔액 조회 성공")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
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
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message), HttpStatus.NOT_FOUND)
        }
    }
}