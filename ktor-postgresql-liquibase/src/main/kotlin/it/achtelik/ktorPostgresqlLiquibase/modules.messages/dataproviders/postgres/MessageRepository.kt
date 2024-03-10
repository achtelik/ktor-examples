package it.achtelik.ktorPostgresqlLiquibase.modules.messages.dataproviders.postgres

import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.Message
import it.achtelik.ktorPostgresqlLiquibase.basics.DatabaseConfig
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class MessageRepository {
    suspend fun find(): List<Message> = DatabaseConfig.dbQuery {
        MessageEntity.selectAll().map(MessageEntity::map)
    }

    suspend fun insert(message: Message): Message? = DatabaseConfig.dbQuery {
        val insertStatement = MessageEntity.insert {
            it[id] = message.id
            it[content] = message.content
            it[createdAt] = message.createdAt
        }
        insertStatement.resultedValues?.singleOrNull()?.let(MessageEntity::map)
    }
}
