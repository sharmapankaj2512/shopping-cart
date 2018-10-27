package com.pankaj.shoppingcart.command.usecases

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.model.Item
import com.pankaj.shoppingcart.command.model.ItemId
import com.pankaj.shoppingcart.command.model.Order
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.model.ShoppingCartEntry
import com.pankaj.shoppingcart.command.usecases.checkoutShoppingCart.CheckoutShoppingCart
import com.pankaj.shoppingcart.command.usecases.event.ShoppingCartCheckedOut
import com.pankaj.shoppingcart.command.usecases.ports.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CreateOrderRepository
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindItemsById
import com.pankaj.shoppingcart.command.usecases.ports.repositories.FindShoppingCartById
import com.pankaj.shoppingcart.command.usecases.ports.repositories.UpdateShoppingCart
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class CheckoutShoppingCartTest : Spek({
    val findShoppingCartById = mockk<FindShoppingCartById>()
    val findItemsById = mockk<FindItemsById>()
    val createOrder = mockk<CreateOrderRepository>()
    val updateShoppingCart = mockk<UpdateShoppingCart>()
    val eventPublisher = mockk<EventPublisher>()

    val checkoutCart = CheckoutShoppingCart(
            findShoppingCartById,
            updateShoppingCart,
            findItemsById,
            createOrder,
            eventPublisher
    )

    group("GIVEN a customer") {
        val customer = Customer(Email("p@p.com"))

        group("WHEN the cart has items") {
            val orderSlot = slot<Order>()
            val cartSlot = slot<ShoppingCart>()

            beforeGroup {
                val itemId = ItemId("123-1231-155")
                val cartEntry = ShoppingCartEntry(itemId, 2)
                val shoppingCart = ShoppingCart(customer.id(), setOf(cartEntry))

                every { findShoppingCartById.findById(customer.id()) } returns Mono.just(shoppingCart)
                every { findItemsById.find(setOf(itemId)) } returns Flux.create { Item(itemId) }
                every { createOrder.create(capture(orderSlot)) } answers { Mono.just(firstArg<Order>()) }
                every { updateShoppingCart.update(capture(cartSlot)) } answers { Mono.just(firstArg<ShoppingCart>()) }
                every { eventPublisher.publish(any<ShoppingCartCheckedOut>()) } just Runs
            }

            test("THEN create an order") {
                StepVerifier.create(checkoutCart(customer.id())).consumeNextWith { order ->
                    assertThat(order).isNotNull
                    assertThat(orderSlot.captured.lineItems).isEqualTo(1)
                    assertThat(cartSlot.captured.isEmpty()).isTrue()
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