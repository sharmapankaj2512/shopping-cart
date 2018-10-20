package com.pankaj.shoppingcart.command.usecases.registerCustomer

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email

data class CustomerInput(val email: String) {
    fun toCustomer(): Customer = Customer(Email(email))
}
