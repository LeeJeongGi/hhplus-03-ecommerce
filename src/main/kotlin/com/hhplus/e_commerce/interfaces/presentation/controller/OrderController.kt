package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.business.dto.OrderSaveDto
import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.facade.OrderFacade
import com.hhplus.e_commerce.interfaces.presentation.request.OrderRequest
import com.hhplus.e_commerce.interfaces.presentation.response.OrderResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderFacade: OrderFacade
) {

    /**
     * 5. 주문 결제 API
     */
    @Operation(summary = "주문 요청", description = "사용자가 주문을 요청합니다.")
    @ApiResponse(responseCode = "200", description = "주문 결제 성공")
    @ApiResponse(responseCode = "404", description = "주문 정보를 찾을 수 없음")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @PostMapping
    fun order(@RequestBody request: OrderRequest): ResponseEntity<OrderResponse> {
        val orderSaveDto = OrderSaveDto(
            userId = request.userId,
            totalAmount = request.totalAmount,
            products = request.products.map { ProductOrderDto.from(it) }
        )

        val result = orderFacade.saveOrder(orderSaveDto = orderSaveDto)
        return ResponseEntity.ok(OrderResponse.from(result))
    }
}