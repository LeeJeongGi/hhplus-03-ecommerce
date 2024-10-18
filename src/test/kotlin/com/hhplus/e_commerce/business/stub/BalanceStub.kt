package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.Balance
import com.hhplus.e_commerce.business.entity.User

object BalanceStub {

    fun create(
        user: User,
        amount: Int = 0
    ): Balance {
        return Balance(
            user = user,
            amount = amount,
        )
    }
}