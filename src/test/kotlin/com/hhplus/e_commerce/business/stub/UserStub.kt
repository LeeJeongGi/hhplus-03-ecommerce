package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.User

object UserStub {

    fun create(name: String): User = User(name = name)
}