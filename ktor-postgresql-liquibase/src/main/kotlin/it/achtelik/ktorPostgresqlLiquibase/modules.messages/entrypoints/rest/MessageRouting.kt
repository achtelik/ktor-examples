package it.achtelik.ktorMongodbMongock.modules.messages.entrypoints.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.MessageService
import java.time.Instant

fun Route.routeMessages(messageService: MessageService) {
    route("/messages") {
        getMessages(messageService)
        postMessages(messageService)
    }
}

private fun Route.getMessages(messageService: MessageService) {
    get() {
        call.respond(messageService.find().map(MessageDTO.Companion::map))
    }
}

private fun Route.postMessages(messageService: MessageService) {
    post() {
        when (val result = messageService.insert("TEST ${Instant.now()}")) {
            null -> call.respond("ERROR")
            else -> call.respond(MessageDTO.map(result))
        }
    }
}

