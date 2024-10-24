package com.hhplus.e_commerce.infra.jpa

import com.hhplus.e_commerce.business.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<User, Long> {
}