package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.common.error.code.ErrorCode
import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val message: String,
    val customErrorCode: String,
    val requestPath: String? = null,
) {

    private constructor(errorCode: ErrorCode, requestPath: String) : this(
        message = errorCode.message,
        customErrorCode = errorCode.errorCode,
        requestPath = requestPath
    )

    companion object {
        fun toResponseEntity(errorCode: ErrorCode, requestPath: String): ResponseEntity<ErrorResponse> {
            return ResponseEntity
                .status(errorCode.httpStatus)
                .body(ErrorResponse(errorCode, requestPath))
        }
    }
}
