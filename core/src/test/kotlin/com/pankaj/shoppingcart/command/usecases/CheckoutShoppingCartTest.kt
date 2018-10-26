package com.pankaj.shoppingcart.command.usecases

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.model.ItemId
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.model.ShoppingCartEntry
import com.pankaj.shoppingcart.command.usecases.checkoutShoppingCart.CheckoutShoppingCart
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class CheckoutShoppingCartTest : Spek({
    val findShoppingCartById = mockk<FindShoppingCartById>()
    val checkoutCart = CheckoutShoppingCart(findShoppingCartById)

    group("GIVEN a customer") {
        val customer = Customer(Email("p@p.com"))

        group("WHEN the cart has items") {
            val itemId = ItemId("123-1231-155")

            beforeGroup {
                val cartEntry = ShoppingCartEntry(itemId, 2)
                val shoppingCart = ShoppingCart(customer.id(), setOf(cartEntry))

                every { findShoppingCartById.findById(customer.id()) } returns Mono.just(shoppingCart)
            }

            test("THEN create an order") {
                StepVerifier.create(checkoutCart(customer.id())).consumeNextWith { order ->
                    assertThat(order).isNotNull
                }
            }
        }

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