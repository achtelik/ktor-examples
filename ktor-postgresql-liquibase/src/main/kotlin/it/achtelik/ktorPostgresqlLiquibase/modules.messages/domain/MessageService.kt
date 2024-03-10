package it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain

import it.achtelik.ktorPostgresqlLiquibase.modules.messages.dataproviders.postgres.MessageRepository

class MessageService(
    private val messageRepository: MessageRepository
) {

    suspend fun find(): List<Message> {
        return messageRepository.find()
    }

    suspend fun insert(content: String): Message? {
        val message = Message(content)
        return messageRepository.insert(message)
    }

}
