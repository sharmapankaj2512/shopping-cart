package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.Order
import reactor.core.publisher.Mono

interface CreateOrderRepository {
    fun create(order: Order): Mono<Order>
}
