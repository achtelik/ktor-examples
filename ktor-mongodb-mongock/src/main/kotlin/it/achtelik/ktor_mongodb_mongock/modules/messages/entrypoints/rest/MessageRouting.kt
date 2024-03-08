package it.achtelik.ktor_mongodb_mongock.modules.messages.entrypoints.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.achtelik.ktor_mongodb_mongock.modules.messages.dataproviders.mongo.MessageRepository
import java.time.Instant

private val messageRepository = MessageRepository()

fun Route.routeMessages() {
    route("/messages") {
        getMessages()
        postMessages()
    }
}

private fun Route.getMessages() {
    get() {
        call.respond(messageRepository.find().map(MessageDTO.Companion::map))
    }
}

private fun Route.postMessages() {
    post() {
        when (val result = messageRepository.insertOne("TEST ${Instant.now()}")) {
            null -> call.respond("ERROR")
            else -> call.respond(MessageDTO.map(result))
        }
    }
}

