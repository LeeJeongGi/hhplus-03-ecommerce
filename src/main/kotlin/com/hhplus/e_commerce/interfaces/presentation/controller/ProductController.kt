package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.common.ErrorResponse
import com.hhplus.e_commerce.common.error.exception.BusinessException
import com.hhplus.e_commerce.interfaces.presentation.response.ProductResponse
import com.hhplus.e_commerce.interfaces.presentation.response.ProductSummary
import com.hhplus.e_commerce.interfaces.presentation.response.TopProductsResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/products")
class ProductController {

    /**
     * 3. 상품 조회 API
     */
    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable productId: Long
    ): ResponseEntity<Any> {
        return try {
            val productResponse = ProductResponse(
                productId = productId,
                name = "상품명",
                category = "카테고리",
                price = 20000,
                stockQuantity = 10
            )
            ResponseEntity.ok(productResponse)
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message), HttpStatus.NOT_FOUND)
        }
    }

    /**
     * 4. 상위 TOP5 상품 조회 API
     */
    @GetMapping("/top")
    fun getTopProducts(
        @RequestParam("limit") limit: Int,
        @RequestParam(value = "days", defaultValue = "3") days: Int
    ): ResponseEntity<Any> {
        return try {
            val topProducts = listOf(
                ProductSummary(
                    productId = 123,
                    name = "상품명1",
                    category = "카테고리",
                    price = 15000,
                    salesCount = 100
                ),
                ProductSummary(
                    productId = 456,
                    name = "상품명2",
                    category = "카테고리",
                    price = 12000,
                    salesCount = 85
                )
            ).take(limit) // limit 수만큼 상품 정보 반복

            ResponseEntity.ok(TopProductsResponse(topProducts))
        } catch (e: BusinessException.NotFound) {
            ResponseEntity(ErrorResponse(code = e.errorCode.errorCode, message = e.errorCode.message), HttpStatus.NOT_FOUND)
        }
    }
}