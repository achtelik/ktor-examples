package it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain

interface MessageRepositoryInterface {
    suspend fun find(): List<Message>

    suspend fun insert(message: Message): Message?
}
