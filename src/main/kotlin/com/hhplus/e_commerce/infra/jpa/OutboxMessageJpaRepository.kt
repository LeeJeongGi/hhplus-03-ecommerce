package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.OutboxMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OutboxMessageJpaRepository: JpaRepository<OutboxMessage, Long> {

    @Query("SELECT o FROM OutboxMessage o WHERE o.eventType = :eventType")
    fun findByStatus(eventType: String): List<OutboxMessage>
}