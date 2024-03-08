package it.achtelik.ktor_mongodb_mongock

import io.ktor.server.application.*
import io.ktor.server.routing.*
import it.achtelik.ktor_mongodb_mongock.basics.configureContentNegotiationAndSerialization
import it.achtelik.ktor_mongodb_mongock.basics.mongo.configureMongo
import it.achtelik.ktor_mongodb_mongock.modules.messages.entrypoints.rest.routeMessages

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
