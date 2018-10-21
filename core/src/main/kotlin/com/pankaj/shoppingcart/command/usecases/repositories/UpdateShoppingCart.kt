package com.pankaj.shoppingcart.command.usecases.repositories

import com.pankaj.shoppingcart.command.model.ShoppingCart
import reactor.core.publisher.Mono

interface UpdateShoppingCart {
    fun update(capture: ShoppingCart): Mono<ShoppingCart>
}
