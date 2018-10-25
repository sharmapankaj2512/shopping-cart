package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.Customer
import reactor.core.publisher.Mono

interface CreateCustomerRepository {
    fun create(customer: Customer): Mono<Customer>
}