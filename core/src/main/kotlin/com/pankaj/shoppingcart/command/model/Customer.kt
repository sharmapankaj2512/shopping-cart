package com.pankaj.shoppingcart.command.model

import java.util.UUID.randomUUID

class Customer(
        private val email: Email,
        private val id: CustomerId = CustomerId()) : Aggregate<CustomerId> {

    override fun id(): CustomerId = id

    fun hasEmail(email: String): Boolean = this.email == Email(email)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Customer
        if (id() != other.id()) return false
        return true
    }

    override fun hashCode(): Int {
        return id().hashCode()
    }


}

data class CustomerId(val id: String = randomUUID().toString()) : Id
