package com.pankaj.shoppingcart.command.repositories

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerExistsRepository
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CreateCustomerRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class InMemoryCustomerRepository : CreateCustomerRepository, CustomerExistsRepository {
    companion object {
        val customersByEmail = mutableMapOf<Email, Customer>()
        val customersById = mutableMapOf<CustomerId, Customer>()
    }

    override fun create(customer: Customer): Mono<Customer> {
        customersByEmail[customer.email()] = customer
        customersById[customer.id()] = customer
        return Mono.just(customer)
    }

    override fun exists(customerId: CustomerId): Boolean = customersById.containsKey(customerId)

    override fun exists(email: Email): Boolean = customersByEmail.containsKey(email)
}