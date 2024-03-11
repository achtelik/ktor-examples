package it.achtelik.ktorPostgresqlLiquibase.basics

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import liquibase.command.CommandScope
import liquibase.command.core.UpdateCommandStep
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager


fun Application.configureDatabases() {
    val url = environment.config.propertyOrNull("postgresql.url")?.getString() ?: ""
    val user = environment.config.propertyOrNull("postgresql.user")?.getString() ?: ""
    val password = environment.config.propertyOrNull("postgresql.password")?.getString() ?: ""

    DatabaseConfig.configure(url, user, password)

    transaction { }
}

object DatabaseConfig {
    fun configure(url: String, user: String, password: String) {
        runLiquibase(url, user, password)
        // Connect to database and use it as default.
        Database.connect(url = url, user = user, password = password)
    }

    private fun runLiquibase(url: String, user: String, password: String) {
        val connection = DriverManager.getConnection(url, user, password)
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))
        val updateCommand = CommandScope(*UpdateCommandStep.COMMAND_NAME)
        updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database)
        updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, "dbmigration/changelog.xml")
        updateCommand.execute()
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        // This command uses the default Database connection. But you could also give a database connection as parameter.
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
