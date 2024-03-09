package it.achtelik.ktorSolace.modules.messages.entrypoints

import io.ktor.server.application.*
import it.achtelik.ktorSolace.basics.solace.SolaceConfig
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger("MessageListener-KtorQtest")
private val messageReceiver = SolaceConfig.startPersistentMessageReceiver("KtorQtest")

private var messageCount = 1
private val messageStack = mutableListOf<String>()
fun Application.listenForMessages() {
    messageReceiver.receiveAsync {
        val payload = it.payloadAsString
        messageStack.add(0, payload)
        LOGGER.info(
            """******** Start Message $messageCount *****************
            $payload
            ******** End Message $messageCount *****************""".trimIndent()
        )
        messageCount++
        messageReceiver.ack(it) //Acknowledge message - Positiv feedback to Solace. Solace will delete the message from the queue.
        while (messageStack.size > 100) messageStack.removeLast()
    }
}