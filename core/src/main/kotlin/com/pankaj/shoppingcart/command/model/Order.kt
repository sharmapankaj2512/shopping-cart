package com.pankaj.shoppingcart.command.model

import java.util.*

class Order(val cart: ShoppingCart, val id: OrderId = OrderId()) : Aggregate<OrderId> {
    override fun id(): OrderId = id
}

data class OrderId(val id: String = UUID.randomUUID().toString()) : Id
