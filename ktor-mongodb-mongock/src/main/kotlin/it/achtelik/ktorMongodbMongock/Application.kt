package it.achtelik.ktorMongodbMongock

import io.ktor.server.application.*
import io.ktor.server.routing.*
import it.achtelik.ktorMongodbMongock.basics.configureContentNegotiationAndSerialization
import it.achtelik.ktorMongodbMongock.basics.mongo.configureMongo
import it.achtelik.ktorMongodbMongock.modules.messages.entrypoints.rest.routeMessages

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureContentNegotiationAndSerialization()
    configureMongo()
    routing {
        routeMessages()
    }
}
