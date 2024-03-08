package it.achtelik.ktor_mongodb_mongock.basics.mongo.migration

import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoDatabase
import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync
import org.bson.Document
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

@ChangeUnit(id = "0001", order = "0001", author = "-")
class `DB-0001-insert_hello_world_message` {
    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    @Execution
    fun execution(database: MongoDatabase) {
        // Do changes with plain Strings to avoid dependency to entities.
        // Entities could change but your scripts should stay the same.
        val document = Document.parse(
            """
                {
                "_id": UUID("${UUID.randomUUID()}"),
                "content":"Hello World",
                "createdAt": ISODate("${Instant.now()}"),
                }
            """.trimIndent()

        )

        // That's the way how Mongock needs to do changes with reactive drivers.
        // Kotlin Coroutine is based on the reactive client.
        // See: https://docs.mongock.io/v5/driver/mongodb-reactive/#code-example
        val subscriber = MongoSubscriberSync<InsertOneResult>()
        database.getCollection("messages").insertOne(document).subscribe(subscriber)
        val result = subscriber.first
        LOGGER.info(result.toString())
    }

    @RollbackExecution
    fun rollbackExecution(database: MongoDatabase) {
        // Nothing to do.
    }
}
