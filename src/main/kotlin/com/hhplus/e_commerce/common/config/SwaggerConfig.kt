package com.hhplus.e_commerce.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info =
            Info()
                .title("E-Commerce API")
                .description("E-Commerce 백엔드 API Swagger Documentation")
                .version("1.0.0")

        val servers =
            listOf(
                Server().description("local").url("http://localhost:8080"),
            )
        return OpenAPI()
            .components(Components())
            .servers(servers)
            .tags(ArrayList())
            .info(info)
    }
}