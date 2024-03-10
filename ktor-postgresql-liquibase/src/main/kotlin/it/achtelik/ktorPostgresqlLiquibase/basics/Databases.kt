package it.achtelik.ktorPostgresqlLiquibase.basics

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import liquibase.command.CommandScope
import liquibase.command.core.UpdateCommandStep
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.sql.Connection
import java.sql.DriverManager


fun Application.configureDatabases() {
    val url = environment.config.propertyOrNull("postgresql.url")?.getString() ?: ""
    val user = environment.config.propertyOrNull("postgresql.user")?.getString() ?: ""
    val password = environment.config.propertyOrNull("postgresql.password")?.getString() ?: ""

    DatabaseConfig.configure(url, user, password)
}

object DatabaseConfig {
    private lateinit var connection: Connection

    fun configure(url: String, user: String, password: String) {
        connection = DriverManager.getConnection(url, user, password)
        runLiquibase(connection)
    }

    fun runLiquibase(connection: Connection) {
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection));
        //val liquibase = Liquibase("dbmigration/changelog.xml", ClassLoaderResourceAccessor(), database)
        //liquibase.update(Contexts(), LabelExpression())
        val updateCommand = CommandScope(*UpdateCommandStep.COMMAND_NAME)
        updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database)
        updateCommand.addArgumentValue<String>(UpdateCommandStep.CHANGELOG_FILE_ARG, "dbmigration/changelog.xml")
        updateCommand.execute()
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
