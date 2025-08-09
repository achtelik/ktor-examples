package it.achtelik.ktorMongodbMongock

import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.*
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import kotlin.test.Test

class ApplicationTest {
    val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:8")
        .waitingFor(Wait.forListeningPort())

    @Test
    fun testRoot() = testApplication {
        mongoDBContainer.start()

        environment {
            config = MapApplicationConfig().apply {
                val defaultConfig = ApplicationConfig("application.conf")
                defaultConfig.keys().forEach { key ->
                    try {
                        listOf(this.put(key, defaultConfig.property(key).getString()))
                    } catch (_: Exception) {
                        listOf(this.put(key, defaultConfig.property(key).getList()))
                    }
                }

                this.put("mongo.connection", mongoDBContainer.connectionString)
            }
        }
    }
}