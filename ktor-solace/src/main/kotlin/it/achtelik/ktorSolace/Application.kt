package it.achtelik.ktorSolace

import io.ktor.server.application.*
import io.ktor.server.routing.*
import it.achtelik.ktorSolace.basics.configureContentNegotiationAndSerialization
import it.achtelik.ktorSolace.basics.solace.configSolace
import it.achtelik.ktorSolace.modules.messages.entrypoints.listenForMessages
import it.achtelik.ktorSolace.modules.messages.entrypoints.routeMessages

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureContentNegotiationAndSerialization()
    configSolace()
    routing {
        routeMessages()
    }
    listenForMessages()
}
