package com.pankaj.shoppingcart.command.usecases.registerCustomer

import com.pankaj.shoppingcart.command.model.Email

interface CustomerExistsRepository {
    fun exists(email: Email): Boolean
}
