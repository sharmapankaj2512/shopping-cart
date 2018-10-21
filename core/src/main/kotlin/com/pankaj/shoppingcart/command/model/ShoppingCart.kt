package com.pankaj.shoppingcart.command.model

class ShoppingCart(private val customerId: CustomerId,
                   private val items: Set<ShoppingCartEntry> = emptySet()) : Aggregate<CustomerId> {

    override fun id(): CustomerId = customerId

    fun add(shoppingCartEntry: ShoppingCartEntry): ShoppingCart =
            ShoppingCart(customerId, items.plus(shoppingCartEntry))

    fun itemCount(): Int = items.count()

    override fun hashCode(): Int {
        return customerId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return customerId == (other as ShoppingCart).customerId
    }
}
