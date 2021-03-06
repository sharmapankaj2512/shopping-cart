package com.pankaj.shoppingcart.command.usecases.ports.repositories

import com.pankaj.shoppingcart.command.model.CustomerId
import com.pankaj.shoppingcart.command.model.Email

interface CustomerExistsRepository {
    fun exists(email: Email): Boolean
    fun exists(customerId: CustomerId): Boolean
}
