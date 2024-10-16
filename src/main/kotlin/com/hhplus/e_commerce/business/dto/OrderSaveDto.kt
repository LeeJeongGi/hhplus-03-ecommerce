package com.hhplus.e_commerce.business.dto

data class OrderSaveDto(
    val userId: Long,
    val totalAmount: Int,
    val products: List<ProductOrderDto>,
){
    companion object {
        private fun OrderSaveDto(userId: Long, totalAmount: Int, products: List<Unit>) {
            TODO("Not yet implemented")
        }
    }
}
