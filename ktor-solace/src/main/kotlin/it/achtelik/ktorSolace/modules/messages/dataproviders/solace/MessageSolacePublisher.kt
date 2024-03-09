package it.achtelik.ktorSolace.modules.messages.dataproviders.solace

import com.solace.messaging.resources.Topic
import it.achtelik.ktorSolace.basics.solace.SolaceConfig
import org.slf4j.LoggerFactory
import java.lang.Exception

class MessageSolacePublisher {
    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    private val persistentMessagePublisher = SolaceConfig.persistentMessagePublisher()

    fun publish(message: String, sendPersistent: Boolean) {
        LOGGER.info("Send message.")
        try {
            persistentMessagePublisher.publish(message, Topic.of("KtorTtest"))
        } catch (e: Exception) {
            LOGGER.error("error")
        }
        LOGGER.info("Send message successful.")
    }
}