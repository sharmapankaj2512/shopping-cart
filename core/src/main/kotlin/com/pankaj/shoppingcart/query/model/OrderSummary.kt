package com.pankaj.shoppingcart.query.model

data class OrderSummary(val customer: Customer, val items: Set<Item> = setOf())
data class Customer(val id: CustomerId, val name: String)
data class Item(val id: ItemId, val name: String, val description: String)

typealias CustomerId = String
typealias ItemId = String