package it.achtelik.ktorMongodbMongock.basics.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver
import io.mongock.runner.standalone.MongockStandalone
import org.bson.UuidRepresentation

class MongoMigration {
    companion object {
        fun execute(connection: String, database: String) {
            // Kotlin Coroutine is based on the reactive client and Mongock doesn"t know the coroutine Driver.
            val client = MongoClients.create(
                MongoClientSettings.builder()
                    // We want to use UUIDs as type at our DOC objects.
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .applyConnectionString(ConnectionString(connection)).build()
            )

            MongockStandalone.builder().setDriver(MongoReactiveDriver.withDefaultLock(client, database))
                .addMigrationScanPackage("${this::class.java.packageName}.migration")
                // If you activate TransactionEnabled, you have to use MongoDB as replica set.
                .setTransactionEnabled(false)
                .buildRunner()
                .execute()
        }
    }
}
