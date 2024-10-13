package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.ProductMetaDto
import com.hhplus.e_commerce.business.dto.ProductSubDto
import com.hhplus.e_commerce.business.repository.ProductRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
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
}