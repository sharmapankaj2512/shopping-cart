package com.pankaj.shoppingcart.command.model

class Customer(private val email: Email) {
    fun hasEmail(email: String): Boolean = this.email == Email(email)
}

data class CustomerId(val id: String)
