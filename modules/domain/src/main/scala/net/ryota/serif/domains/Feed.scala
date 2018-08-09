package net.ryota.serif.domains

import java.time.ZonedDateTime

final class Feed(
                  val id: ID[Feed],
                  val title: String,
                  val text: String,
                  val createdAt: ZonedDateTime
                ) extends Entity {
}


trait FeedRepository[M] extends Repository[Feed, M]