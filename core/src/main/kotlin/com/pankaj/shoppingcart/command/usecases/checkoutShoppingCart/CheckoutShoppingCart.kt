package com.pankaj.shoppingcart.command.usecases.checkoutShoppingCart

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.Order
import com.pankaj.shoppingcart.command.model.OrderId
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import reactor.core.publisher.Mono

class CheckoutShoppingCart(private val findShoppingCartById: FindShoppingCartById) {
    operator fun invoke(id: CustomerId): Mono<OrderId> {
        return findShoppingCartById.findById(id)
                .flatMap { cart ->
                    if (cart.isEmpty()) Mono.error(IllegalStateException(""))
                    else Mono.just(Order(cart).id)
                }
    }
}