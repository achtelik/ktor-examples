package it.achtelik.ktor_mongodb_mongock.modules.messages.dataproviders.mongo

import it.achtelik.ktor_mongodb_mongock.basics.mongo.MongoConfig
import kotlinx.coroutines.flow.toList
import java.time.Instant
import java.util.UUID

class MessageRepository {
    private val collection = MongoConfig.database().getCollection<MessageDOC>("messages")

    suspend fun find(): List<MessageDOC> {
        return collection.find().toList()
    }

    suspend fun insertOne(content: String): MessageDOC? {
        val message = MessageDOC(UUID.randomUUID(), content, Instant.now())
        return when (collection.insertOne(message).wasAcknowledged()) {
            true -> message
            false -> null
        }
    }
}
