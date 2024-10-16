package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.BalanceHistory

interface BalanceHistoryRepository {

    fun save(balanceHistory: BalanceHistory): BalanceHistory

    fun deleteAll()
}