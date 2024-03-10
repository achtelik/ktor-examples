package it.achtelik.ktorMongodbMongock.modules.messages.entrypoints.rest

import it.achtelik.ktorPostgresqlLiquibase.modules.messages.dataproviders.postgres.MessageEntity
import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val id: String,
    val content: String,
    val createdAt: String
) {

    companion object {
        fun map(domain: Message): MessageDTO {
            return MessageDTO(
                id = domain.id.toString(),
                content = domain.content,
                createdAt = domain.createdAt.toString()
            )
        }
    }
}
