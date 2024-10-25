package com.hhplus.e_commerce.common.error.exception

import com.hhplus.e_commerce.common.error.code.ErrorCode

sealed class BusinessException(
    val errorCode: ErrorCode
): RuntimeException(errorCode.message) {

    class NotFound(
        errorCode: ErrorCode,
    ): BusinessException(errorCode)

    class BadRequest(
        errorCode: ErrorCode,
    ) : BusinessException(errorCode)
}