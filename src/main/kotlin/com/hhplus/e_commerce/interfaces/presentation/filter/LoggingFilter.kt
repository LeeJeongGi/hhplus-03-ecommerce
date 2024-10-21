package com.hhplus.e_commerce.interfaces.presentation.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class LoggingFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)

        logger.info(printRequestLog(requestWrapper))
        filterChain.doFilter(requestWrapper, responseWrapper)
        logger.info(printResponseLog(responseWrapper))

        responseWrapper.copyBodyToResponse()  // 이 메소드가 중요합니다.
    }

    private fun printRequestLog(request: ContentCachingRequestWrapper): String {
        val method = request.method
        val uri = request.requestURI
        val headers = request.headerNames.toList().joinToString { "$it: ${request.getHeader(it)}" }
        val body = String(request.contentAsByteArray)

        return """
            ---- Request ----
            Method: $method
            URI: $uri
            Headers: 
            $headers
            Body: 
            $body
        """.trimIndent()
    }

    private fun printResponseLog(response: ContentCachingResponseWrapper): String {
        val status = response.status
        val headers = response.headerNames.joinToString { "$it: ${response.getHeader(it)}" }
        val body = String(response.contentAsByteArray)

        return """
            ---- Response ----
            Status: $status
            Headers: 
            $headers
            Body: 
            $body
        """.trimIndent()
    }
}