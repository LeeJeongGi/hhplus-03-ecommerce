package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.Balance

interface BalanceRepository {

    fun findByUserIdWithLock(userId: Long): Balance?

    fun findByUserId(userId: Long): Balance?

    fun save(balance: Balance): Balance

    fun deleteAll()
}