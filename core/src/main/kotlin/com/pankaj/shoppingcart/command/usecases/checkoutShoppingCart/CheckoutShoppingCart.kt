package com.pankaj.shoppingcart.command.usecases.checkoutShoppingCart

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.Order
import com.pankaj.shoppingcart.command.model.OrderId
import com.pankaj.shoppingcart.command.model.OrderLineItem
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.usecases.event.ShoppingCartCheckedOut
import com.pankaj.shoppingcart.command.usecases.ports.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CreateOrderRepository
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindItemsById
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import com.pankaj.shoppingcart.command.usecases.ports.repositories.UpdateShoppingCart
import reactor.core.publisher.Mono

class CheckoutShoppingCart(private val findShoppingCartById: FindShoppingCartById,
                           private val updateShoppingCart: UpdateShoppingCart,
                           private val findItemsById: FindItemsById,
                           private val createOrder: CreateOrderRepository,
                           private val eventPublisher: EventPublisher) {
    operator fun invoke(id: CustomerId): Mono<OrderId> {
        return findShoppingCartById.findById(id)
                .flatMap { createOrder(it) }
                .doOnSuccess { createOrder.create(it.second) }
                .doOnSuccess { updateShoppingCart.update(it.first.clear()) }
                .doOnSuccess { eventPublisher.publish(ShoppingCartCheckedOut(it.second)) }
                .map { it.second.id() }
    }

    private fun createOrder(cart: ShoppingCart): Mono<Pair<ShoppingCart, Order>> {
        return findItemsById.find(cart.itemIds())
                .map { it to cart.quantityOf(it.id()) }
                .map { OrderLineItem(it.first, it.second) }
                .collectList()
                .map { cart to Order(cart.id(), it.toSet()) }
    }
}