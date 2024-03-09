package it.achtelik.ktorOpenapi.modules.messages

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import it.achtelik.ktorOpenapi.generated.openapi.Paths
import it.achtelik.ktorOpenapi.generated.openapi.models.MessageDto
import it.achtelik.ktorOpenapi.generated.openapi.models.UserDto
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals

private lateinit var messageId: String

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MessagesRoutingKtTest {

    /**
     * Create message
     */
    @Test
    fun test01Post() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post(Paths.OperationMap.messagesPost) {
            contentType(ContentType.Application.Json)
            setBody(MessageDto(text = "Hallo", user = UserDto(name = "Matthias")))
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Message received.", response.bodyAsText())
    }

    /**
     * Get list of message with text filter which contains list with one entry
     */
    @Test
    fun test02Get() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.get(Paths.OperationMap.messagesGet) {
            parameter("text", "Ha")
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<List<MessageDto>>()
        assertEquals(1, body.size)
        assertEquals("Hallo", body[0].text)
        messageId = body[0].id!!
    }

    /**
     * Get message with id
     */
    @Test
    fun test03IdGet() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.get(Paths.OperationMap.messagesIdGet.replace("{id}", messageId))
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<MessageDto>()
        assertEquals(messageId, body.id)
        assertEquals("Hallo", body.text)
    }

    /**
     * Delete all messages
     */
    @Test
    fun test04Delete() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.delete(Paths.OperationMap.messagesDelete)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Messages deleted.", response.bodyAsText())
    }

    /**
     * Get all messages and check that list is empty.
     */
    @Test
    fun test05Get() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.get(Paths.OperationMap.messagesGet)
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<List<MessageDto>>()
        assertEquals(listOf(), body)
    }
}