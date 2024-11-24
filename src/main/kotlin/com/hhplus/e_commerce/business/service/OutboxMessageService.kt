package com.hhplus.e_commerce.business.service

import com.hhplus.e_commerce.business.entity.OutboxMessage
import com.hhplus.e_commerce.business.entity.type.OutboxStatus
import com.hhplus.e_commerce.business.repository.OutboxMessageRepository
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service

@Service
class OutboxMessageService(
    private val outBoxMessageRepository: OutboxMessageRepository,
) {

    fun save(outboxMessage: OutboxMessage): OutboxMessage {
        return outBoxMessageRepository.save(outboxMessage)
    }

    fun updateStatus(outboxMessageId: Long, status: OutboxStatus) {

        // ID로 OutboxMessage 조회
        val outboxMessage = outBoxMessageRepository.findById(outboxMessageId)
            ?: throw BusinessException.NotFound(ErrorCode.OutboxMessage.NOT_FOUND_OUTBOX_MESSAGE)

        // 상태 업데이트
        outboxMessage.update(status)

        // 저장 (상태 변경 반영)
        outBoxMessageRepository.save(outboxMessage)
    }

    fun findByStatus(status: String): List<OutboxMessage> {
        return outBoxMessageRepository.findByStatus(status)
    }

}