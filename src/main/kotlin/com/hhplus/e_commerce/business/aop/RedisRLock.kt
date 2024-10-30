package com.hhplus.e_commerce.business.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component


@Component
class RedisRLock {

    @Throws(Throwable::class)
    fun proceed(joinPoint: ProceedingJoinPoint): Any {
        return joinPoint.proceed()
    }
}