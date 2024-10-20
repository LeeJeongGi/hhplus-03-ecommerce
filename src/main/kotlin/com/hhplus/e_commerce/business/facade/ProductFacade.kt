package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.facade.dto.ProductInfo
import com.hhplus.e_commerce.business.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductFacade(
    private val productService: ProductService
) {

    fun getProduct(productId: Long): ProductInfo {

        val productMetaDto = productService.getProductMetaInfo(productId)
        val productSubDto = productService.getProductSubInfo(productId)

        return ProductInfo.of(productMetaDto, productSubDto)
    }

    fun getAllProducts(pageable: Pageable): Page<ProductInfo> {
        val metaProducts = productService.getAllProducts(pageable)

        return metaProducts.map { productMetaDto ->
            val productSubDto = productService.getProductSubInfo(productMetaDto.productId)
            ProductInfo.of(productMetaDto, productSubDto)
        }
    }
}