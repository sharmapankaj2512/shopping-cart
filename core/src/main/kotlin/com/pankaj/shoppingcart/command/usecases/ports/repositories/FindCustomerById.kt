package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.CustomerId
import reactor.core.publisher.Mono

interface FindCustomerById {
    fun findById(id: CustomerId): Mono<Customer>
}
