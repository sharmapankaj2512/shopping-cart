package com.pankaj.shoppingcart.command.usecases.registerCustomer

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.usecases.event.CustomerRegistered
import com.pankaj.shoppingcart.command.usecases.ports.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CreateCustomerRepository
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

class RegisterCustomer(private val createCustomerRepository: CreateCustomerRepository,
                       private val customerExistsRepository: CustomerExistsRepository,
                       private val publisher: EventPublisher) {
    fun execute(input: CustomerInput): Mono<CustomerId> {
        if (customerExistsRepository.exists(Email(input.email)))
            return Mono.error(IllegalArgumentException())

        return createCustomerRepository.create(input.toCustomer())
                .doOnSuccess { publisher.publish(CustomerRegistered(it)) }
                .map { it.id() }
    }
}

