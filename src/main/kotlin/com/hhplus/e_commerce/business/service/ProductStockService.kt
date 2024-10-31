package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.dto.ProductOrderDto
import com.hhplus.e_commerce.business.entity.ProductStock
import com.hhplus.e_commerce.business.repository.ProductStockRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service

@Service
class ProductStockService(
    private val productStockRepository: ProductStockRepository
) {

    fun valid(productOrders: List<ProductOrderDto>): List<ProductOrderDto> {

        val productStockIds = productOrders.map { it -> it.productStockId }

        val existingIds = productStockRepository.findExistingIds(productStockIds)
        if (productStockIds.size != existingIds.size) {
            throw BusinessException.NotFound(ErrorCode.Product.NOT_FOUND_PRODUCT)
        }

        val productStocks = productStockRepository.findByProductIds(productStockIds)
            .associateBy { it.productId }

        productOrders.forEach { productOrder ->
            val productStock = productStocks[productOrder.productStockId]
                ?: throw BusinessException.NotFound(ErrorCode.Product.NOT_FOUND_PRODUCT)

            if (productStock.quantity < productOrder.quantity) {
                throw BusinessException.BadRequest(ErrorCode.Product.OUT_OF_STOCK)
            }
        }

        return productOrders
    }

    fun updateStock(productOrders: List<ProductOrderDto>): List<ProductOrderDto> {

        val productStocksToUpdate: List<ProductStock> = productOrders.map { productOrder ->
            val productStock = productStockRepository.findById(productOrder.productStockId)
                ?: throw BusinessException.NotFound(ErrorCode.Product.NOT_FOUND_PRODUCT)

            // 주문 수량만큼 재고 차감
            val updatedQuantity = productStock.quantity - productOrder.quantity

            // 수량 업데이트
            productStock.updateQuantity(updatedQuantity)
            productStock
        }

        // 변경된 재고 정보 저장
        productStockRepository.saveAll(productStocksToUpdate)

        return productOrders
    }
}