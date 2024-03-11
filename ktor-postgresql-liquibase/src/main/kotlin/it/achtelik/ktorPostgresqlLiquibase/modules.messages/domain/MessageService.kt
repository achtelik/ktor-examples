package it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain

class MessageService(
    private val messageRepository: MessageRepositoryInterface
) {

    suspend fun find(): List<Message> {
        return messageRepository.find()
    }

    suspend fun insert(content: String): Message? {
        val message = Message(content)
        return messageRepository.insert(message)
    }

}
