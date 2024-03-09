package it.achtelik.ktorOpenapi.modules.messages

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.achtelik.ktorOpenapi.generated.openapi.Paths
import it.achtelik.ktorOpenapi.generated.openapi.models.MessageDto
import java.util.UUID

val messages = mutableListOf<MessageDto>()
fun Routing.messages() {
    get<Paths.messagesGet> { filter ->
        call.respond(
            messages.filter {
                it.text.contains(
                    other = filter.text ?: "", ignoreCase = true
                )
            }
        )
    }

    get<Paths.messagesIdGet> { filter ->
        call.respond(
            messages.first {
                it.id.equals(filter.id)
            }
        )
    }

    post<Paths.messagesPost, MessageDto> { filter, body ->
        messages.add(0, MessageDto(id = UUID.randomUUID().toString(), text = body.text, user = body.user))
        while (messages.size > 100) messages.removeLast()
        call.respond(HttpStatusCode.OK, "Message received.")
    }

    delete<Paths.messagesDelete> {
        messages.clear()
        call.respond("Messages deleted.")
    }
}
