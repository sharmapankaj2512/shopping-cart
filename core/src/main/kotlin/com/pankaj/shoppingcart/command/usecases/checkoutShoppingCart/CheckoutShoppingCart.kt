package com.pankaj.shoppingcart.command.usecases.checkoutShoppingCart

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.OrderId
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
        findShoppingCartById.findById(id)
                .map { findItemsById.find(it.itemIds()) }
                .map { it.map { it to  } }

//                .flatMap(this::createOrder)
//                .doOnSuccess { updateShoppingCart.update(cart) }
//                .doOnSuccess { eventPublisher.publish(ShoppingCartCheckedOut(it)) }
//                .map { it.id }
    }

//    private fun createOrder(cart: ShoppingCart) =
//            if (cart.isEmpty())
//                Mono.error(IllegalStateException("Cart is empty"))
//            else
//                Mono.just(Order(cart))
}