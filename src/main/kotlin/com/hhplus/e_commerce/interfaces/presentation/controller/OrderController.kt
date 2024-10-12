package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.common.error.response.ErrorResponse
import com.hhplus.e_commerce.common.error.exception.BusinessException
import com.hhplus.e_commerce.interfaces.presentation.request.OrderRequest
import com.hhplus.e_commerce.interfaces.presentation.response.OrderProduct
import com.hhplus.e_commerce.interfaces.presentation.response.OrderResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders")
class OrderController {

    /**
     * 5. 주문 결제 API
     */
    @Operation(summary = "주문 결제", description = "사용자가 주문을 결제합니다.")
    @ApiResponse(responseCode = "200", description = "주문 결제 성공")
    @ApiResponse(responseCode = "404", description = "주문 정보를 찾을 수 없음")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @PostMapping
    fun placeOrder(@RequestBody request: OrderRequest): ResponseEntity<Any> {
        return try {
            // 여기서 주문 처리 로직을 구현해야 합니다.
            // 주문이 성공적으로 완료되면 아래와 같이 응답을 반환합니다.
            val orderResponse = OrderResponse(
                balance = 20000,
                orderId = 789,
                products = listOf(
                    OrderProduct(
                        productId = 123,
                        quantity = 2,
                        price = 30000,
                        totalAmount = 60000
                    ),
                    OrderProduct(
                        productId = 456,
                        quantity = 1,
                        price = 15000,
                        totalAmount = 15000
                    )
                )
            )
            ResponseEntity.ok(orderResponse)
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(
                ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message),
                HttpStatus.NOT_FOUND
            )
        } catch (e: BusinessException.BadRequest) {
            ResponseEntity(
                ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message),
                HttpStatus.BAD_REQUEST
            )
        }
    }
}