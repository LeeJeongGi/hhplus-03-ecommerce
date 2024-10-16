package com.hhplus.e_commerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
class ECommerceApplication

fun main(args: Array<String>) {
	runApplication<ECommerceApplication>(*args)
}
