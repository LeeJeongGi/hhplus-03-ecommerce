package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.ProductMetaDto
import com.hhplus.e_commerce.business.dto.ProductStatsDto
import com.hhplus.e_commerce.business.dto.ProductSubDto
import com.hhplus.e_commerce.business.repository.ProductOrderStatsRepository
import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productOrderStatsRepository: ProductOrderStatsRepository
) {

    fun getProductMetaInfo(productId: Long): ProductMetaDto {
        val product = productRepository.findById(productId)
            ?: throw BusinessException.NotFound(ErrorCode.Product.NOT_FOUND_PRODUCT)

        return ProductMetaDto.from(product)
    }

    fun getProductSubInfo(productId: Long): List<ProductSubDto> {
        val products = productRepository.findStocksByProductId(productId)
        return products.map { ProductSubDto(it.size, it.quantity) }
    }

    fun getTop5Products(limit: Int, days: Int): List<ProductStatsDto> {
        val topProducts = productOrderStatsRepository.getTop5Products(
            lastWeekDate = LocalDateTime.now().minusDays(days.toLong()),
            pageable = PageRequest.of(0, limit)
        )

        return topProducts.map { productOrderStats ->
            val productId = productOrderStats.productId

            val product = productRepository.findById(productId)
                ?: throw BusinessException.NotFound(ErrorCode.Product.NOT_FOUND_PRODUCT)

            ProductStatsDto(
                productId = productId,
                name = product.name,
                category = product.category,
                price = product.price,
                salesCount = productOrderStats.totalSalesAmount.toInt(),
            )
        }
    }

    fun getAllProducts(pageable: Pageable): Page<ProductMetaDto> {
        val productsPage = productRepository.findAllProduct(pageable)

        return productsPage.map { product ->
            ProductMetaDto.from(product)
        }
    }

}