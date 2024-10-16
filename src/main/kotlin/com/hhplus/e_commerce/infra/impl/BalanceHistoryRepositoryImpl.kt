package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.BalanceHistory
import com.hhplus.e_commerce.business.repository.BalanceHistoryRepository
import com.hhplus.e_commerce.infra.jpa.BalanceHistoryJpaRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceHistoryRepositoryImpl(
    private val balanceHistoryJpaRepository: BalanceHistoryJpaRepository
): BalanceHistoryRepository {

    override fun save(balanceHistory: BalanceHistory): BalanceHistory {
        return balanceHistoryJpaRepository.save(balanceHistory)
    }

    override fun deleteAll() {
        balanceHistoryJpaRepository.deleteAll()
    }


}