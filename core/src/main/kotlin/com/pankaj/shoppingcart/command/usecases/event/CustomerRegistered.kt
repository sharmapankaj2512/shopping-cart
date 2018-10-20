package com.pankaj.shoppingcart.command.usecases.event

import com.pankaj.shoppingcart.command.model.Customer

class CustomerRegistered(customer: Customer) {
    val id = customer.id()
    val email = customer.email()
}
