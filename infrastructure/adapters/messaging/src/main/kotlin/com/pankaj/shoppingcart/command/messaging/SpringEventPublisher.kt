package com.pankaj.shoppingcart.command.messaging

import com.pankaj.shoppingcart.command.usecases.ports.publisher.EventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher : EventPublisher {
    override fun <T : Any> publish(event: T) {
        //TO BE IMPLEMENTED
    }
}