package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.business.stub.ProductStockStub
import com.hhplus.e_commerce.business.stub.ProductStub
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @MockK
    private lateinit var productRepository: ProductRepository

    @InjectMockKs
    private lateinit var productService: ProductService

    @Test
    @DisplayName("상품 조회 실패 테스트 - 등록되지 않은 상품 조회시 실패하는 테스트")
    fun getProductException1() {
        // given
        every { productRepository.findById(1L) } returns null

        // when
        val message = org.junit.jupiter.api.assertThrows<BusinessException.NotFound> {
            productService.getProductMetaInfo(1L)
        }.message

        // then
        assertThat(message).isEqualTo(ErrorCode.Product.NOT_FOUND_PRODUCT.message)
    }

    @Test
    @DisplayName("상품 조회 성공 테스트 - 등록된 상품 조회시 상품 주 정보 조회하는 테스트")
    fun getProductMainInfoTest() {
        // given
        val product = ProductStub.create(
            name = "Lee",
            category = "TOP",
            price = 1000,
        )

        every { productRepository.findById(1L) } returns product

        // when
        val productMetaInfo = productService.getProductMetaInfo(1L)

        // then
        assertThat(productMetaInfo).isNotNull
        assertThat(productMetaInfo.name).isEqualTo(product.name)
        assertThat(productMetaInfo.price).isEqualTo(product.price)
    }

    @Test
    @DisplayName("상품 조회 성공 테스트 - 등록된 상품 조회시 상품 부가 정보 조회하는 테스트")
    fun getProductSubInfoTest() {
        // given
        val productId = 1L
        val productStocks = ProductStockStub.createMultiple(productId)

        every { productRepository.findStocksByProductId(1L) } returns productStocks

        // when
        val productSubInfo = productService.getProductSubInfo(1L)

        // then
        assertThat(productSubInfo.size).isEqualTo(productStocks.size)
        productStocks.forEachIndexed { index, productStock ->
            val dto = productSubInfo[index]
            assertThat(dto.size).isEqualTo(productStock.size)
            assertThat(dto.quantity).isEqualTo(productStock.quantity)
        }
    }
}