package it.achtelik.ktor_mongodb_mongock.modules.messages.entrypoints.rest

import it.achtelik.ktor_mongodb_mongock.modules.messages.dataproviders.mongo.MessageDOC
import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val id: String,
    val content: String,
    val createdAt: String
) {

    companion object {
        fun map(doc: MessageDOC): MessageDTO {
            return MessageDTO(
                id = doc.id.toString(),
                content = doc.content,
                createdAt = doc.createdAt.toString()
            )
        }
    }
}
