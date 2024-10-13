package com.hhplus.e_commerce.business.entity

import com.hhplus.e_commerce.business.entity.type.ProductCategory
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "category")
    val category: String,

    @Column(name = "price", nullable = false)
    val price: Int,
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}