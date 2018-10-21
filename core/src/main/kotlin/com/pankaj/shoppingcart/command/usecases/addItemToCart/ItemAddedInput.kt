package com.pankaj.shoppingcart.command.usecases.addItemToCart

data class ItemAddedInput(val customerId: String, val itemId: String, val quantity: Int)
