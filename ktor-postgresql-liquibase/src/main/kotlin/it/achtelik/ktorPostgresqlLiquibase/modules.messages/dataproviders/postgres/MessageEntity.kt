package it.achtelik.ktorPostgresqlLiquibase.modules.messages.dataproviders.postgres

import it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain.Message
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object MessageEntity : Table() {
    val id = uuid("id")
    val content = text("content")
    val createdAt = timestamp("createdAt")

    override val primaryKey = PrimaryKey(id)

    fun map(row: ResultRow): Message {
        return Message(row[id], row[content], row[createdAt])
    }
}
