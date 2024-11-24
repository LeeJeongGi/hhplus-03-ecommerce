package com.hhplus.e_commerce.business.entity

import com.hhplus.e_commerce.business.entity.type.OutboxStatus
import jakarta.persistence.*

@Entity
data class OutboxMessage(

    @Enumerated(EnumType.STRING)
    var eventType: OutboxStatus,
    val payload: String,
): BaseEntity() {

    fun update(status: OutboxStatus) {
        this.eventType = status
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    companion object {
        fun from(jsonData: String): OutboxMessage {
            return OutboxMessage(
                eventType = OutboxStatus.READY,
                payload = jsonData,
            )
        }
    }
}