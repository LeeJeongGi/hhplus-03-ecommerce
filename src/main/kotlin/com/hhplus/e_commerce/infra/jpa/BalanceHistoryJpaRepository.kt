package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.BalanceHistory
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceHistoryJpaRepository: JpaRepository<BalanceHistory, Long> {

}