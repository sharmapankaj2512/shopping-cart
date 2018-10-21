package com.pankaj.shoppingcart.command.repositories

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.usecases.repositories.CreateCustomerRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class InMemoryCustomerRepository : CreateCustomerRepository {
    companion object {
        val customers = mutableMapOf<Email, Customer>()
    }

    override fun create(customer: Customer): Mono<Customer> {
        customers[customer.email()] = customer
        return Mono.just(customer)
    }
}