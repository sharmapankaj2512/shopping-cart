package com.pankaj.shoppingcart.command.model

class Item(private val itemId: ItemId) {
    fun id(): ItemId = itemId
}
