package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.business.dto.BalanceChargeDto
import com.hhplus.e_commerce.business.facade.BalanceFacade
import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.interfaces.presentation.request.ChargeRequest
import com.hhplus.e_commerce.interfaces.presentation.response.ChargeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class BalanceController(
    private val balanceFacade: BalanceFacade,
    private val balanceService: BalanceService,
) {

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
    ): ResponseEntity<ChargeResponse> {
        val userBalance = balanceFacade.charge(BalanceChargeDto.of(userId, request.amount))
        return ResponseEntity.ok(ChargeResponse.from(userBalance))
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
    ): ResponseEntity<ChargeResponse> {
        val userBalance = balanceService.getUserBalance(userId)
        return ResponseEntity.ok(ChargeResponse.from(userBalance))
    }
}