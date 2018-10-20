package com.pankaj.shoppingcart.repositories

import com.pankaj.shoppingcart.model.Customer
import com.pankaj.shoppingcart.model.CustomerId
import reactor.core.publisher.Mono

interface CreateCustomerRepository {
    fun create(toCustomer: Customer): Mono<CustomerId>
}