package com.pankaj.shoppingcart.command.usecases

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.model.ShoppingCart
import com.pankaj.shoppingcart.command.usecases.addItemToCart.AddItemToShoppingCart
import com.pankaj.shoppingcart.command.usecases.addItemToCart.ItemAddedInput
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerExistsRepository
import com.pankaj.shoppingcart.command.usecases.repositories.FindShoppingCartById
import com.pankaj.shoppingcart.command.usecases.repositories.UpdateShoppingCart
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions
import org.spekframework.spek2.Spek
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

object AddItemToShoppingCartTest : Spek({
    val customerExistsRepository = mockk<CustomerExistsRepository>()
    val findShoppingCartById = mockk<FindShoppingCartById>()
    val updateShoppingCart = mockk<UpdateShoppingCart>()

    val usecase = AddItemToShoppingCart(customerExistsRepository, findShoppingCartById, updateShoppingCart)

    group("GIVEN a customer that exists") {
        val customer = Customer(Email("p.p@p.com"))

        beforeGroup {
            every { customerExistsRepository.exists(customer.id()) } returns true
        }

        group("AND an empty shopping cart") {
            val shoppingCart = ShoppingCart(customer.id())
            val cartSlot = slot<ShoppingCart>()

            beforeGroup {
                every { findShoppingCartById.findById(customer.id()) } returns Mono.just(shoppingCart)
                every { updateShoppingCart.update(capture(cartSlot)) } answers { Mono.just(firstArg<ShoppingCart>()) }
            }

            group("WHEN an item is added to the cart") {
                val input = ItemAddedInput(customer.id().id, "item-id", 1)

                test("THEN cart is updated and had one item in it") {
                    StepVerifier.create(usecase.execute(input)).consumeNextWith {
                        Assertions.assertThat(cartSlot.captured.itemCount()).isEqualTo(1)
                    }
                }
            }
        }

//        group("GIVEN a customer that does not exists") {}
    }
})