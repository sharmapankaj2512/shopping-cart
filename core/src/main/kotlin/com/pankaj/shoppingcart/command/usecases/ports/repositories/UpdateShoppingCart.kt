package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.ShoppingCart
import reactor.core.publisher.Mono

interface UpdateShoppingCart {
    fun update(cart: ShoppingCart): Mono<ShoppingCart>
}
