package com.pankaj.shoppingcart.command.model

interface Aggregate<out ID : Id> {
    fun id(): ID
}

interface Id
