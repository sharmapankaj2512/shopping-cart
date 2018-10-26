package com.pankaj.shoppingcart.command.repositories

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import com.pankaj.shoppingcart.command.usecases.ports.repositories.UpdateShoppingCart
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class InMemoryShoppingCartRepository : FindShoppingCartById, UpdateShoppingCart {
    companion object {
        val shoppingCarts = mutableMapOf<CustomerId, ShoppingCart>()
    }

    override fun update(cart: ShoppingCart): Mono<ShoppingCart> {
        shoppingCarts[cart.id()] = cart
        return Mono.just(cart)
    }

    override fun findById(id: CustomerId): Mono<ShoppingCart> {
        return Mono.just(shoppingCarts.computeIfAbsent(id) { ShoppingCart(it, emptySet()) })
    }
}