package com.hhplus.e_commerce.business.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "balance",
    indexes = [Index(name = "idx_user_id", columnList = "user_id")]
)
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

    @Column(name = "amount", nullable = false)
    var amount: Int = amount
        protected set

    fun updateAmount(amount: Int) {
        this.amount += amount
    }
}