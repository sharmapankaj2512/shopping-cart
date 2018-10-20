package com.pankaj.shoppingcart.usecases

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.pankaj.shoppingcart.model.Customer
import com.pankaj.shoppingcart.repositories.CreateCustomerRepository
import com.pankaj.shoppingcart.usecases.registerCustomer.CustomerInput
import com.pankaj.shoppingcart.usecases.registerCustomer.RegisterCustomer
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.argThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object RegisterCustomerTest : Spek({
    val repository = mock(CreateCustomerRepository::class.java)
    val usecase = RegisterCustomer(repository)

    describe("Given a customer") {
        var input: CustomerInput

        describe("with valid details") {
            input = CustomerInput("p.p@p.com")

            it("it is created") {
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