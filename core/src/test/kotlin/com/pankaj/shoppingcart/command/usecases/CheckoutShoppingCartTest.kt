package com.pankaj.shoppingcart.command.usecases

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.usecases.checkoutShoppingCart.CheckoutShoppingCart
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.lang.IllegalStateException

class CheckoutShoppingCartTest : Spek({
    val findShoppingCartById = mockk<FindShoppingCartById>()
    val checkoutCart = CheckoutShoppingCart(findShoppingCartById)

    group("GIVEN a customer") {
        val customer = Customer(Email("p@p.com"))

        group("WHEN the cart has items") {}
        group("WHEN the cart does not have items") {

            beforeGroup {
                every { findShoppingCartById.findById(customer.id()) } returns Mono.just(ShoppingCart(customer.id()))
            }

            test("THEN raise an error mentioning cart is empty") {
                StepVerifier.create(checkoutCart(customer.id()))
                        .expectError(IllegalStateException::class.java)
            }
        }
    }
})