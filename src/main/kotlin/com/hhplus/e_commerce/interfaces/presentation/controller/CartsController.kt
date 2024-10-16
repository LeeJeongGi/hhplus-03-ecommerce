package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.business.dto.CartsSaveDto
import com.hhplus.e_commerce.business.service.CartsService
import com.hhplus.e_commerce.interfaces.presentation.request.CartsRequest
import com.hhplus.e_commerce.interfaces.presentation.response.CartsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users/{userId}/carts")
class CartsController(
    private val cartsService: CartsService,
) {

    /**
     * 6. 장바구니 상품 조회 API
     */
    @Operation(summary = "장바구니 상품 조회", description = "사용자의 장바구니에 담긴 상품을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 상품 조회 성공")
    @GetMapping
    fun getBasketItems(
        @PathVariable userId: Long
    ): ResponseEntity<List<CartsResponse>> {
        val results = cartsService.findByUserId(userId)
        return ResponseEntity.ok(results.map { CartsResponse.from(it) })
    }

    /**
     * 7. 장바구니 상품 추가 API
     */
    @Operation(summary = "장바구니 상품 추가", description = "사용자의 장바구니에 상품을 추가합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 업데이트 성공")
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    @PostMapping("/{productId}")
    fun updateBasket(
        @PathVariable userId: Long,
        @RequestBody request: CartsRequest
    ): ResponseEntity<CartsResponse> {
        val cartsSaveDto = cartsService.save(CartsSaveDto.of(userId, request.productId))
        return ResponseEntity.ok(CartsResponse.from(cartsSaveDto))
    }

    /**
     * 7. 장바구니 상품 삭제 API
     */
    @Operation(summary = "장바구니 상품 삭제", description = "사용자의 장바구니에 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 업데이트 성공")
    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    @DeleteMapping("/{productId}")
    fun deleteCartItem(
        @PathVariable userId: Long,
        @RequestBody request: CartsRequest
    ): ResponseEntity<CartsResponse> {
        val carts = cartsService.deleteByUserIdAndProductId(userId, request.productId)
        return ResponseEntity.ok(CartsResponse.from(carts))
    }
}