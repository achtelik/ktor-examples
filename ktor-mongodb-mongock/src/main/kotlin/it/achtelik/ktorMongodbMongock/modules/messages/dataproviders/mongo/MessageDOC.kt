package it.achtelik.ktorMongodbMongock.modules.messages.dataproviders.mongo

import org.bson.codecs.pojo.annotations.BsonId
import java.time.Instant
import java.util.UUID

data class MessageDOC(
    @BsonId
    val id: UUID,
    val content: String,
    val createdAt: Instant
)
