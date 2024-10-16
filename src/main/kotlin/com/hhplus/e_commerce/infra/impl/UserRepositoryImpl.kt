package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.User
import com.hhplus.e_commerce.business.repository.UserRepository
import com.hhplus.e_commerce.infra.jpa.UserJpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
): UserRepository {

    override fun save(user: User): User = userJpaRepository.save(user)

    override fun findById(userId: Long): User? = userJpaRepository.findById(userId).getOrNull()

    override fun deleteAll() = userJpaRepository.deleteAll()

}