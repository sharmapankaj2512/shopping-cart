package com.pankaj.shoppingcart.command.model

import java.util.UUID.randomUUID

class Customer(private val email: Email) : Aggregate<CustomerId> {
    override fun id(): CustomerId = CustomerId()

    fun hasEmail(email: String): Boolean = this.email == Email(email)
}

data class CustomerId(val id: String = randomUUID().toString()) : Id
