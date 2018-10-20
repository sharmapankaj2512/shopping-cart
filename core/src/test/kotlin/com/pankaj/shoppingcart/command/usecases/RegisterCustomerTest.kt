package com.pankaj.shoppingcart.command.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doAnswer
import com.pankaj.shoppingcart.command.model.Customer
import com.pankaj.shoppingcart.command.usecases.publisher.EventPublisher
import com.pankaj.shoppingcart.command.usecases.registerCustomer.CustomerInput
import com.pankaj.shoppingcart.command.usecases.registerCustomer.RegisterCustomer
import com.pankaj.shoppingcart.command.usecases.repositories.CreateCustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.mockito.AdditionalAnswers
import org.mockito.AdditionalAnswers.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import reactor.core.publisher.Mono


object RegisterCustomerTest : Spek({
    val repository = mock(CreateCustomerRepository::class.java)
    val publisher = mock(EventPublisher::class.java)
    val usecase = RegisterCustomer(repository, publisher)

    group("Given a customer") {
        lateinit var input: CustomerInput

        beforeGroup {
            doAnswer { Mono.just(returnsFirstArg<Customer>()) }.`when`(repository).create(any())
        }

        group("With valid details") {

            beforeGroup {
                input = CustomerInput("p.p@p.com")
            }

            test("then customer is created") {
                usecase.execute(input)

                argumentCaptor<Customer>().apply {
                    verify(repository).create(capture())

                    assertThat(allValues.size).isEqualTo(1)
                    assertThat(firstValue.hasEmail(input.email)).isTrue()
                }
            }
        }
    }
})