package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<Order, Long> {

}