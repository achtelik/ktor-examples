package it.achtelik.ktorPostgresqlLiquibase

import io.ktor.server.application.*
import io.ktor.server.routing.*
import it.achtelik.ktorMongodbMongock.modules.messages.entrypoints.rest.routeMessages
import it.achtelik.ktorPostgresqlLiquibase.modules.messages.dataproviders.postgres.MessageRepository
import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.MessageService
import it.achtelik.ktorPostgresqlLiquibase.basics.configureDatabases
import it.achtelik.ktorPostgresqlLiquibase.basics.configureResources
import it.achtelik.ktorPostgresqlLiquibase.basics.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureResources()
    routing {
        routeMessages(MessageService(MessageRepository()))
    }
}
