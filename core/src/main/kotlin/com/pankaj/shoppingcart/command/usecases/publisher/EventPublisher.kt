package com.pankaj.shoppingcart.command.usecases.publisher

interface EventPublisher {
    fun <T : Any> publish(event: T)
}
