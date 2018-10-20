package com.pankaj.shoppingcart.command.usecases.registerCustomer

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.usecases.event.CustomerRegistered
import com.pankaj.shoppingcart.command.usecases.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.repositories.CreateCustomerRepository
import reactor.core.publisher.Mono

class RegisterCustomer(private val repository: CreateCustomerRepository,
                       private val publisher: EventPublisher) {
    fun execute(input: CustomerInput): Mono<CustomerId> {
        return repository.create(input.toCustomer())
                .doOnSuccess { publisher.publish(CustomerRegistered(it)) }
                .map { it.id() }
    }
}

