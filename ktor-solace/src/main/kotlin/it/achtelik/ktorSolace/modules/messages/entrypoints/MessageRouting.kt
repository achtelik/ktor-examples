package it.achtelik.ktorSolace.modules.messages.entrypoints

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.achtelik.ktorSolace.modules.messages.dataproviders.solace.MessageSolacePublisher
import java.time.Instant

private val messageSolacePublisher = MessageSolacePublisher()

fun Route.routeMessages() {
    route("/messages") {
        postMessages()
    }
}

private fun Route.postMessages() {
    post() {
        val sendPersistent = call.parameters["send_persistent"].toBoolean()
        messageSolacePublisher.publish("TEST ${Instant.now()}", sendPersistent)
        call.respond("OK")
    }
}

