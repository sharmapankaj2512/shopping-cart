package com.pankaj.shoppingcart.query.repositories

import com.pankaj.shoppingcart.query.model.CustomerId
import com.pankaj.shoppingcart.query.model.OrderSummary
import org.springframework.stereotype.Repository

@Repository
class InMemoryOrderSummaryRepository {
    companion object {
        val orderSummaries = mutableMapOf<CustomerId, OrderSummary>()
    }

    fun createSummary(orderSummary: OrderSummary) {
        orderSummaries[orderSummary.customer.id] = orderSummary
    }
}