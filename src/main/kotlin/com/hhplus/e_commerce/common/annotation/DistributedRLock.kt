package com.hhplus.e_commerce.common.annotation

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedRLock(
    val key: String,
    val waitTime: Long = 10,
    val leaseTime: Long = 5,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
)