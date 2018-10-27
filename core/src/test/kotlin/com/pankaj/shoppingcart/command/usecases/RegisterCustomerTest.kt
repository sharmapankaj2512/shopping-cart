package com.pankaj.shoppingcart.command.usecases

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.model.Email
import com.pankaj.shoppingcart.command.usecases.event.CustomerRegistered
import com.pankaj.shoppingcart.command.usecases.ports.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CustomerExistsRepository
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerInput
import com.pankaj.shoppingcart.command.usecases.registerCustomer.RegisterCustomer
import com.pankaj.shoppingcart.command.usecases.ports.repositories.CreateCustomerRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


object RegisterCustomerTest : Spek({
    val publisher = mockk<EventPublisher>()
    val createCustomerRepository = mockk<CreateCustomerRepository>()
    val customerExistsRepository = mockk<CustomerExistsRepository>()
    val registerCustomer = RegisterCustomer(createCustomerRepository, customerExistsRepository, publisher)

    group("GIVEN a customer") {
        lateinit var input: CustomerInput

        group("WITH valid details") {
            val customer = slot<Customer>()

            beforeGroup {
                input = CustomerInput("p.p@p.com")

                every { createCustomerRepository.create(capture(customer)) } answers { Mono.just(firstArg<Customer>()) }
                every { customerExistsRepository.exists(Email(input.email)) } returns false
                every { publisher.publish(any<CustomerRegistered>()) } just Runs
            }

            test("THEN customer is created") {
                StepVerifier.create(registerCustomer(input)).consumeNextWith { customerId ->
                    assertThat(customer.captured.hasEmail(input.email)).isTrue()
                    assertThat(customerId).isEqualTo(customer.captured.id())
                }
            }
        }

        group("THAT already exists") {
            beforeGroup {
                input = CustomerInput("p.p@p.com")

                every { customerExistsRepository.exists(Email(input.email)) } returns true
            }

            test("THEN customer is not updated and error is returned") {
                StepVerifier.create(registerCustomer(input))
                        .expectError(IllegalArgumentException::class.java)
            }
        }
    }
})