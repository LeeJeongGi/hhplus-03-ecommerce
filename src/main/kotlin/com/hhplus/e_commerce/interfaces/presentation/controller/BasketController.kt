package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.common.error.response.ErrorResponse
import com.hhplus.e_commerce.common.error.exception.BusinessException
import com.hhplus.e_commerce.interfaces.presentation.request.BasketUpdateRequest
import com.hhplus.e_commerce.interfaces.presentation.response.BasketItem
import com.hhplus.e_commerce.interfaces.presentation.response.BasketResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class BasketController {

    /**
     * 6. 장바구니 상품 조회 API
     */
    @GetMapping("/users/{userId}/basket")
    fun getBasketItems(
        @PathVariable userId: Long
    ): ResponseEntity<Any> {
        return try {
            val basketResponse = BasketResponse(
                basketItems = listOf(
                    BasketItem(productId = 123, quantity = 2),
                    BasketItem(productId = 456, quantity = 1)
                )
            )
            ResponseEntity.ok(basketResponse)
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message), HttpStatus.NOT_FOUND)
        }
    }

    /**
     * 7. 장바구니 상품 추가/삭제 API
     */
    @PostMapping("/users/{userId}/basket")
    fun updateBasket(
        @PathVariable userId: Long,
        @RequestBody request: BasketUpdateRequest
    ): ResponseEntity<Any> {
        return try {
            // 장바구니 업데이트 로직 구현
            val basketResponse = BasketResponse(
                basketItems = listOf(
                    BasketItem(productId = 123, quantity = 2),
                    BasketItem(productId = 456, quantity = 1)
                )
            )
            ResponseEntity.ok(basketResponse)
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message), HttpStatus.NOT_FOUND)
        }
    }
}