package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.business.facade.ProductFacade
import com.hhplus.e_commerce.business.service.ProductService
import com.hhplus.e_commerce.interfaces.presentation.response.ProductResponse
import com.hhplus.e_commerce.interfaces.presentation.response.ProductSummary
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productFacade: ProductFacade,
    private val productService: ProductService,
) {

    /**
     * 3. 상품 조회 API
     */
    @Operation(summary = "상품 조회", description = "주어진 ID에 해당하는 상품의 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 정보 조회 성공")
    @ApiResponse(responseCode = "404", description = "상품 정보를 찾을 수 없음")
    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable productId: Long
    ): ResponseEntity<ProductResponse> {
        val productInfo = productFacade.getProduct(productId)
        return ResponseEntity.ok(ProductResponse.from(productInfo))
    }

    /**
     * 4. 상위 TOP5 상품 조회 API
     */
    @Operation(summary = "상위 TOP5 상품 조회", description = "판매량 기준으로 상위 N개의 상품 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상위 상품 정보 조회 성공")
    @ApiResponse(responseCode = "404", description = "상품 정보를 찾을 수 없음")
    @GetMapping("/top")
    fun getTopProducts(
        @RequestParam("limit") limit: Int,
        @RequestParam(value = "days", defaultValue = "3") days: Int
    ): ResponseEntity<List<ProductSummary>> {
        val products = productService.getTop5Products(limit, days)
        val result = products.map { ProductSummary.from(it) }
        return ResponseEntity.ok(result)
    }
}