package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.Payment

interface PaymentRepository {

    fun save(payment: Payment): Payment

    fun deleteAll()

}