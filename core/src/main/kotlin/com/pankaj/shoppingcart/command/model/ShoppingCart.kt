package com.pankaj.shoppingcart.command.model

class ShoppingCart(private val customerId: CustomerId,
                   private val items: Set<ShoppingCartEntry> = emptySet()) : Aggregate<CustomerId> {

    val itemQuantities = items.map { it.itemId to it.quantity }.toMap()

    override fun id(): CustomerId = customerId

    fun add(shoppingCartEntry: ShoppingCartEntry): ShoppingCart =
            ShoppingCart(customerId, items.plus(shoppingCartEntry))

    fun itemCount(): Int = items.count()

    fun itemIds(): Set<ItemId> = itemQuantities.keys

    fun isEmpty(): Boolean = itemCount() == 0

    fun quantityOf(id: ItemId): Int = itemQuantities.getOrDefault(id, 0)

    fun clear(): ShoppingCart = ShoppingCart(customerId, emptySet())

    override fun hashCode(): Int {
        return customerId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return customerId == (other as ShoppingCart).customerId
    }
}
