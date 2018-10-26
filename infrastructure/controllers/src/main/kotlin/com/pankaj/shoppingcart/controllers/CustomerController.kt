package com.pankaj.shoppingcart.controllers

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.usecases.ports.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CreateCustomerRepository
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerExistsRepository
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerInput
import com.pankaj.shoppingcart.command.usecases.registerCustomer.RegisterCustomer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
class CustomerController(createCustomerRepository: CreateCustomerRepository,
                         customerExistsRepository: CustomerExistsRepository,
                         eventPublisher: EventPublisher) {

    val registerCustomer = RegisterCustomer(
            createCustomerRepository,
            customerExistsRepository,
            eventPublisher
    )

    @PostMapping("/customers")
    fun registerCustomers(input: CustomerInput): Mono<CustomerId> = registerCustomer(input = input)
}