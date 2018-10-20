package com.pankaj.shoppingcart.usecases.registerCustomer

import com.pankaj.shoppingcart.model.CustomerId
import com.pankaj.shoppingcart.repositories.CreateCustomerRepository
import reactor.core.publisher.Mono

class RegisterCustomer(private val repository: CreateCustomerRepository) {
    fun execute(input: CustomerInput): Mono<CustomerId> =
            repository.create(input.toCustomer())
}

