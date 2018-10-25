package com.pankaj.shoppingcart.command.usecases.ports.publisher

interface EventPublisher {
    fun <T : Any> publish(event: T)
}
