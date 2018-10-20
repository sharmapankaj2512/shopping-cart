package com.pankaj.shoppingcart.command.usecases.registerCustomer

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.repositories.CreateCustomerRepository
import reactor.core.publisher.Mono

class RegisterCustomer(private val repository: CreateCustomerRepository) {
    fun execute(input: CustomerInput): Mono<CustomerId> =
            repository.create(input.toCustomer())
}

