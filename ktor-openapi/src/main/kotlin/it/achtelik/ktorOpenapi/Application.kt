package it.achtelik.ktorOpenapi

import io.ktor.server.application.*
import io.ktor.server.routing.*
import it.achtelik.ktorOpenapi.basics.configureSwaggerUI
import it.achtelik.ktorOpenapi.basics.configureResources
import it.achtelik.ktorOpenapi.basics.configureSerialization
import it.achtelik.ktorOpenapi.modules.messages.messages

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.main() {
    configureSwaggerUI()
    configureSerialization()
    configureResources()
    routing {
        messages()
        getAllRoutes().forEach { println(it) }
    }
}
