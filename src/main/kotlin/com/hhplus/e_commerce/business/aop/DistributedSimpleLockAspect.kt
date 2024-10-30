package com.hhplus.e_commerce.business.aop

import com.hhplus.e_commerce.common.annotation.DistributedSimpleLock
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.util.*

@Aspect
@Component
class DistributedSimpleLockAspect(
    private val redisSimpleLock: RedisSimpleLock,
) {

    @Around("@annotation(com.hhplus.e_commerce.common.annotation.DistributedSimpleLock)")
    fun around(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedSimpleLock = method.getAnnotation(DistributedSimpleLock::class.java)

        val lockKey = distributedSimpleLock.key
        val lockValue = UUID.randomUUID().toString()

        try {
            val acquired = redisSimpleLock.tryLock(
                key = lockKey,
                value = lockValue,
                leaseTime = distributedSimpleLock.leaseTime,
                timeUnit = distributedSimpleLock.timeUnit
            )

            if (!acquired) {
                throw BusinessException.BadRequest(ErrorCode.Common.BAD_REQUEST)
            }

            return joinPoint.proceed()
        } finally {
            redisSimpleLock.releaseLock(lockKey, lockValue)
        }
    }

}