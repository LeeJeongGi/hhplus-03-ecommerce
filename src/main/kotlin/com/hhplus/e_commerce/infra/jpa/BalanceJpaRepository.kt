package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Balance
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface BalanceJpaRepository: JpaRepository<Balance, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Balance b where b.user.id = :userId")
    fun findByUserIdWithLock(userId: Long): Balance?

    fun findByUserId(userId: Long): Balance?
}