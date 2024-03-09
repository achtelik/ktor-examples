package it.achtelik.ktorSolace.basics.solace

import com.solace.messaging.MessagingService
import com.solace.messaging.config.SolaceProperties
import com.solace.messaging.config.profile.ConfigurationProfile
import com.solace.messaging.publisher.PersistentMessagePublisher
import com.solace.messaging.receiver.PersistentMessageReceiver
import com.solace.messaging.resources.Queue
import io.ktor.server.application.*
import java.util.*


fun Application.configSolace() {
    val properties = Properties();
    properties.setProperty(
        SolaceProperties.TransportLayerProperties.HOST,
        environment.config.propertyOrNull("solace.host")?.getString() ?: ""
    )
    properties.setProperty(
        SolaceProperties.ServiceProperties.VPN_NAME,
        environment.config.propertyOrNull("solace.vpn")?.getString() ?: ""
    )
    properties.setProperty(
        SolaceProperties.AuthenticationProperties.SCHEME_BASIC_USER_NAME,
        environment.config.propertyOrNull("solace.username")?.getString() ?: ""
    )
    properties.setProperty(
        SolaceProperties.AuthenticationProperties.SCHEME_BASIC_PASSWORD,
        environment.config.propertyOrNull("solace.password")?.getString() ?: ""
    )
    properties.setProperty(SolaceProperties.ServiceProperties.RECEIVER_DIRECT_SUBSCRIPTION_REAPPLY, "true");

    // TODO: Configuring Connection Time-Outs and Retries https://docs.solace.com/API/API-Developer-Guide/Configuring-Connection-T.htm#Sample
    // -1 = unlimited retries
    properties.setProperty(SolaceProperties.TransportLayerProperties.RECONNECTION_ATTEMPTS, "-1")

    SolaceConfig.configure(properties)
}

object SolaceConfig {
    private lateinit var messagingService: MessagingService
    private lateinit var persistentMessagePublisher: PersistentMessagePublisher


    fun configure(properties: Properties) {
        messagingService = MessagingService.builder(ConfigurationProfile.V1)
            .fromProperties(properties)    // Enables property based configuration, required to set host and VPN properties
            .build()                       // Returns a MessagingService object based on the provided configuration
            .connect();                    // A synchronous connection is established with an event broker.

        persistentMessagePublisher = messagingService.createPersistentMessagePublisherBuilder()
            .onBackPressureReject(1000)     // Creates a message buffer with space to accommodate 1000 messages, at which point messages are rejected until there is room.
            .build()             // Builds a PersistentMessagePublisher object based on the provided configuration.
            .start();            // Causes the service to resume regular duties. Before this method is called, the service is considered off-duty.
    }

    fun persistentMessagePublisher(): PersistentMessagePublisher {
        return persistentMessagePublisher
    }

    fun startPersistentMessageReceiver(queueName: String): PersistentMessageReceiver {
        return messagingService.createPersistentMessageReceiverBuilder()
            .build(Queue.durableNonExclusiveQueue(queueName))
            .start()
    }
}