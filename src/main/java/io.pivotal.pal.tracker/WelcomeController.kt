package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController constructor(@Value("\${WELCOME_MESSAGE}") message: String) {

    var message: String? = null

    init {
        this.message = message
    }

    @GetMapping("/")
    fun sayHello(): String? {
        return message
    }
}