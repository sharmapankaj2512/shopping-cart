package com.pankaj.shoppingcart.command.repositories

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.CustomerId
import reactor.core.publisher.Mono

interface CreateCustomerRepository {
    fun create(toCustomer: Customer): Mono<CustomerId>
}