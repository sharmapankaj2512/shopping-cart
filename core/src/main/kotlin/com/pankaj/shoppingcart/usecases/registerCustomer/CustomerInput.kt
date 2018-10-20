package com.pankaj.shoppingcart.usecases.registerCustomer

import com.pankaj.shoppingcart.model.Customer
import com.pankaj.shoppingcart.model.Email

data class CustomerInput(val email: String) {
    fun toCustomer(): Customer = Customer(Email(email))
}
