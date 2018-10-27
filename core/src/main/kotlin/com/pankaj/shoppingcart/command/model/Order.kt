package com.pankaj.shoppingcart.command.model

import java.util.*

class Order(val customerId: CustomerId, val lineItems: Set<OrderLineItem>, val id: OrderId = OrderId()) : Aggregate<OrderId> {
    override fun id(): OrderId = id
}

data class OrderId(val id: String = UUID.randomUUID().toString()) : Id

data class OrderLineItem(val item: Item, val quantity: Int)
