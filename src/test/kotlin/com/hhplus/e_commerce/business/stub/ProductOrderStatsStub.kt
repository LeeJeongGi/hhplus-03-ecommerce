package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.ProductOrderStats
import java.time.LocalDateTime

object ProductOrderStatsStub {

    fun create(
        productId: Long,
        orderDate: LocalDateTime,
        totalOrder: Long,
        totalSalesAmount: Long,
    ): ProductOrderStats {
        return ProductOrderStats(
            productId = productId,
            orderDate = orderDate,
            totalOrder = totalOrder,
            totalSalesAmount = totalSalesAmount,
        )
    }
}