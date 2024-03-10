package it.achtelik.ktorPostgresqlLiquibase.modules.messages.domain

import java.time.Instant
import java.util.UUID

class Message(
    val id: UUID,
    val content: String,
    val createdAt: Instant
) {
    constructor(content: String) : this(UUID.randomUUID(), content, Instant.now())

    init {
        when {
            content.isEmpty() -> throw IllegalArgumentException("Content can't be empty!")
        }
    }
}
