package com.hhplus.e_commerce.business.aop

import com.hhplus.e_commerce.common.annotation.DistributedRLock
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAspect(
    val redissonClient: RedissonClient,
    val redisRLock: RedisRLock
) {

    @Around("@annotation(com.hhplus.e_commerce.common.annotation.DistributedRLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributeRLock = method.getAnnotation(DistributedRLock::class.java)

        val lockKey = distributeRLock.key
        val rLock = redissonClient.getLock(lockKey)

        try {
            val available = rLock.tryLock(distributeRLock.waitTime, distributeRLock.leaseTime, distributeRLock.timeUnit)

            if (!available) {
                throw BusinessException.BadRequest(ErrorCode.Common.LOCK_ACQUISITION_FAILED)
            }

            return redisRLock.proceed(joinPoint)

        } catch (e: Exception) {
            Thread.currentThread().interrupt()
            throw BusinessException.BadRequest(ErrorCode.Common.LOCK_ACQUISITION_FAILED)
        } finally {
            rLock.unlock()
        }
    }
}