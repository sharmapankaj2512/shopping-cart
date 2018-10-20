package com.pankaj.shoppingcart.command.model

data class Email(private val email: String) {
    fun text(): String = email
}
