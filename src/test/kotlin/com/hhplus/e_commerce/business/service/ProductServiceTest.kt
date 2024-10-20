package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.repository.ProductOrderStatsRepository
import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.business.stub.ProductOrderStatsStub
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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @MockK
    private lateinit var productRepository: ProductRepository

    @MockK
    private lateinit var productOrderStatsRepository: ProductOrderStatsRepository

    @InjectMockKs
    private lateinit var productService: ProductService

    @Test
    @DisplayName("상품 조회 실패 테스트 - 등록되지 않은 상품 조회시 실패하는 테스트")
    fun getProductException1() {
        // given
        every { productRepository.findById(1L) } returns null

        // when
        val message = assertThrows<BusinessException.NotFound> {
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

    @Test
    @DisplayName("상품 조회 성공 테스트 - 성공적으로 TOP 5 상품 정보를 조회할 수 있는지 검증")
    fun getTop5ProductsSuccess() {
        // given
        val days = 3
        val limit = 5
        val lastWeekDate = LocalDateTime.now().minusDays(days.toLong())

        val productOrderStatsList = listOf(
            ProductOrderStatsStub.create(1L, lastWeekDate, 6, 25000),
            ProductOrderStatsStub.create(2L, lastWeekDate, 5, 23000),
            ProductOrderStatsStub.create(3L, lastWeekDate, 4, 22000),
            ProductOrderStatsStub.create(4L, lastWeekDate, 3, 20000),
            ProductOrderStatsStub.create(5L, lastWeekDate, 2, 19000),
        )

        val product1 = ProductStub.create("adidas", "TOP", 15000)
        val product2 = ProductStub.create("nike", "TOP", 17000)
        val product3 = ProductStub.create("newBalance", "TOP", 18000)
        val product4 = ProductStub.create("hex", "TOP", 19000)
        val product5 = ProductStub.create("brand", "TOP", 20000)

        every { productOrderStatsRepository.getTop5Products(any(), any()) } returns productOrderStatsList
        every { productRepository.findById(1L) } returns product1
        every { productRepository.findById(2L) } returns product2
        every { productRepository.findById(3L) } returns product3
        every { productRepository.findById(4L) } returns product4
        every { productRepository.findById(5L) } returns product5

        // when
        val result = productService.getTop5Products(limit, days)

        // then
        assertThat(result).hasSize(5)
        assertThat(result[0].name).isEqualTo(product1.name)
        assertThat(result[0].price).isEqualTo(product1.price)

        assertThat(result[1].name).isEqualTo(product2.name)
        assertThat(result[1].price).isEqualTo(product2.price)

        assertThat(result[2].name).isEqualTo(product3.name)
        assertThat(result[2].price).isEqualTo(product3.price)

        assertThat(result[3].name).isEqualTo(product4.name)
        assertThat(result[3].price).isEqualTo(product4.price)

        assertThat(result[4].name).isEqualTo(product5.name)
        assertThat(result[4].price).isEqualTo(product5.price)
    }
}