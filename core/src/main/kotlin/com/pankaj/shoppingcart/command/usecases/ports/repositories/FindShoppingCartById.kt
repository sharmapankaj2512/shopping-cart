package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.ShoppingCart
import reactor.core.publisher.Mono

interface FindShoppingCartById {
    fun findById(id: CustomerId): Mono<ShoppingCart>
}
