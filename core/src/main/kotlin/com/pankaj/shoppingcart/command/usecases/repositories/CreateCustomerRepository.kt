package com.pankaj.shoppingcart.command.usecases.repositories

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.CustomerId
import reactor.core.publisher.Mono

interface CreateCustomerRepository {
    fun create(customer: Customer): Mono<Customer>
}