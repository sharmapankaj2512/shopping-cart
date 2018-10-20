package com.pankaj.shoppingcart.usecases

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.internal.bytebuddy.description.type.TypeDefinition.Sort.describe
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object RegisterCustomerTest : Spek({
    describe("Given a customer") {
        describe("with valid details") {
            it("is created") {
                assertThat(true).isTrue()
            }
        }
    }
})