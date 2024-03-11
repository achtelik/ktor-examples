package it.achtelik.ktorPostgresqlLiquibase.modules.messages.dataproviders.postgres

import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.Message
import it.achtelik.ktorPostgresqlLiquibase.basics.DatabaseConfig
import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.MessageRepositoryInterface
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class MessageRepository : MessageRepositoryInterface {
    override suspend fun find(): List<Message> = DatabaseConfig.dbQuery {
        MessageEntity.selectAll().map(MessageEntity::map)
    }

    override suspend fun insert(message: Message): Message? = DatabaseConfig.dbQuery {
        val insertStatement = MessageEntity.insert {
            it[id] = message.id
            it[content] = message.content
            it[createdAt] = message.createdAt
        }
        insertStatement.resultedValues?.singleOrNull()?.let(MessageEntity::map)
    }
}
