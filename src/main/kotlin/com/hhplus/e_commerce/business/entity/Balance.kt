package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
class Balance(
    user: User,
    amount: Int,
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var user: User = user
        protected set

    @Version
    var version: Long = 0

    @Column(name = "amount", nullable = false)
    var amount: Int = amount
        protected set

    fun updateAmount(amount: Int) {
        this.amount += amount
    }
}