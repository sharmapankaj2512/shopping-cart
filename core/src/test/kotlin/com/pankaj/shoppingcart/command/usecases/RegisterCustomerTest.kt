package com.pankaj.shoppingcart.command.usecases

import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.usecases.event.CustomerRegistered
import com.pankaj.shoppingcart.command.usecases.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerInput
import com.pankaj.shoppingcart.command.usecases.registerCustomer.RegisterCustomer
import com.pankaj.shoppingcart.command.usecases.repositories.CreateCustomerRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import reactor.core.publisher.Mono


object RegisterCustomerTest : Spek({
    val repository = mockk<CreateCustomerRepository>()
    val publisher = mockk<EventPublisher>()
    val usecase = RegisterCustomer(repository, publisher)

    group("GIVEN a customer") {
        lateinit var input: CustomerInput

        group("WITH valid details") {
            val customer = slot<Customer>()

            beforeGroup {
                input = CustomerInput("p.p@p.com")

                every { repository.create(capture(customer)) } answers { Mono.just(firstArg<Customer>()) }
                every { publisher.publish(any<CustomerRegistered>()) } just Runs
            }

            test("THEN customer is created") {
                val customerId = usecase.execute(input).block()

                assertThat(customer.captured.hasEmail(input.email)).isTrue()
                assertThat(customerId).isEqualTo(customer.captured.id())
            }
        }
    }
})