package com.pankaj.shoppingcart.command.usecases.addItemToCart

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.ItemId
import com.pankaj.shoppingcart.command.model.ShoppingCartEntry
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerExistsRepository
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import com.pankaj.shoppingcart.command.usecases.ports.repositories.UpdateShoppingCart
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

class AddItemToShoppingCart(private val customerExistsRepository: CustomerExistsRepository,
                            private val findShoppingCartById: FindShoppingCartById,
                            private val updateShoppingCart: UpdateShoppingCart) {

    operator fun invoke(input: ItemAddedInput): Mono<CustomerId> {
        val customerId = CustomerId(input.customerId)
        if (!customerExistsRepository.exists(customerId))
            return Mono.error(IllegalArgumentException("Not a valid customer"))

        return findShoppingCartById.findById(customerId)
                .map { it.add(ShoppingCartEntry(ItemId(input.itemId), input.quantity)) }
                .doOnSuccess { updateShoppingCart.update(it) }
                .map { it.id() }
    }

}