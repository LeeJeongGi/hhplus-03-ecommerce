package com.hhplus.e_commerce.interfaces.presentation.handler

import com.hhplus.e_commerce.common.error.exception.BusinessException
import com.hhplus.e_commerce.interfaces.presentation.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.bind.annotation.ExceptionHandler

@RestControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        e: BusinessException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.error(
            "[Business Exception] " +
                    "- http status code : ${e.errorCode.httpStatus}" +
                    "- custom error code : ${e.errorCode.errorCode} " +
                    "- message : ${e.errorCode.message}",
        )
        return ErrorResponse.toResponseEntity(e.errorCode, request.requestURI)
    }

}