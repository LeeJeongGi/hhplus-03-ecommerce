package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.Balance
import com.hhplus.e_commerce.business.repository.BalanceRepository
import com.hhplus.e_commerce.infra.jpa.BalanceJpaRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceRepositoryImpl(
    private val balanceJpaRepository: BalanceJpaRepository
): BalanceRepository {

    override fun findByUserIdWithLock(userId: Long): Balance? {
        return balanceJpaRepository.findByUserIdWithLock(userId)
    }

    override fun findByUserId(userId: Long): Balance? {
        return balanceJpaRepository.findByUserId(userId)
    }

    override fun save(balance: Balance): Balance {
        return balanceJpaRepository.save(balance)
    }

    override fun deleteAll() {
        return balanceJpaRepository.deleteAll()
    }
}