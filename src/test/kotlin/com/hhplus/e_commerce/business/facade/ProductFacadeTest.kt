package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.ProductMetaDto
import com.hhplus.e_commerce.business.dto.ProductSubDto
import com.hhplus.e_commerce.business.service.ProductService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.system.measureTimeMillis
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ProductFacadeTest {

    @MockK
    lateinit var productService: ProductService

    @InjectMockKs
    lateinit var productFacade: ProductFacade

    @Test
    @DisplayName("상품 조회 테스트 : 정상적으로 상품 정보를 조회할 수 있다.")
    fun getProductTest() {
        // given
        val productId = 1L
        val productMetaDto = ProductMetaDto(productId, "Product Name", 1000)
        val productSubDto = ProductSubDto("Description", 10)

        every { productService.getProductMetaInfo(productId) } returns productMetaDto
        every { productService.getProductSubInfo(productId) } returns listOf(productSubDto)

        // when

        // 캐시가 없는 상태에서 성능 측정
        val timeWithoutCache = measureTimeMillis {
            repeat(100000) {
                productFacade.getProduct(productId)
            }
        }

        // 캐시가 있는 상태에서 성능 측정
        productFacade.getProduct(productId) // 캐시에 저장
        val timeWithCache = measureTimeMillis {
            repeat(100000) { productFacade.getProduct(productId) }
        }

        println("캐시 미적용 상태 조회 시간: ${timeWithoutCache}ms")
        println("캐시 적용 상태 조회 시간: ${timeWithCache}ms")

        // 캐시 적용 시 시간이 더 짧은지 검증
        assertThat(timeWithCache).isLessThan(timeWithoutCache)

    }

}