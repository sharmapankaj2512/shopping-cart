package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.Item
import com.pankaj.shoppingcart.command.model.ItemId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FindItemsById {
    fun find(itemIds: Set<ItemId>): Flux<Item>
}
