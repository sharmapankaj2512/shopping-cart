package com.pankaj.shoppingcart.model

class Customer(private val email: Email) {
    fun hasEmail(email: String): Boolean = this.email == Email(email)
}

data class CustomerId(val id: String)
