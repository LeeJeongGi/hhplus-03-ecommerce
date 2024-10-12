package com.hhplus.e_commerce.business.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
        private set
}