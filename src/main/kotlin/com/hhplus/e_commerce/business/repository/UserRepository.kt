package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.User

interface UserRepository {

    fun save(user: User): User

    fun findById(userId: Long): User?

    fun deleteAll()
}