package net.ryota.infra.repositoryImpl

import java.time.ZoneId

import net.ryota.infra.{AsyncDBAction, Tables}
import net.ryota.serif.domains
import net.ryota.serif.domains.{Feed, FeedRepository, ID, UsesFeedRepository}
import slick.jdbc.PostgresProfile.api._

object FeedRepositoryRDBImpl extends FeedRepository[AsyncDBAction] {
  private def toDomain(feedRow: Tables.FeedsRow) = {
     new Feed(
        id = ID.apply(feedRow.id),
        createdAt = feedRow.createdAt.toLocalDate.atStartOfDay(ZoneId.systemDefault()),
        title =  domains.Feed.Title(feedRow.title),
        describe = domains.Feed.Describe(feedRow.describe)
      )
  }

  private var feedMap: Map[ID[Feed], Feed] = Map.empty
  override def delete(id: domains.ID[Feed]): AsyncDBAction[Unit] = ???

  override def findById(id: domains.ID[Feed]): AsyncDBAction[Option[domains.Entity]] = AsyncDBAction.withCtxFuture { implicit ec =>
    val query = Tables.Feeds.filter(_.id === id.value)

    ec.database.run(query.result).map { feedRows =>
      feedRows.map(toDomain).headOption
    } (ec)

  }

  override def findByIds(ids: Seq[domains.ID[Feed]]): AsyncDBAction[Seq[Feed]] = AsyncDBAction.withCtxFuture { implicit ctx =>
    ctx.database.run(Tables.Feeds.filter(_.id.inSet(ids.map(_.value).toSet)).result).map(_.map(toDomain))(ctx)
  }

  override def store(e: Feed): AsyncDBAction[Unit] = AsyncDBAction {
    println("call FeedRepositoryOnMemory")

    feedMap = feedMap + (e.id -> e)

    println(feedMap.size)
  }

  def findAll(): AsyncDBAction[Seq[Feed]] = AsyncDBAction {
    feedMap.values.toSeq
  }

}

trait MixInFeedRepository extends UsesFeedRepository[AsyncDBAction] {
  val feedRepository: FeedRepository[AsyncDBAction] = FeedRepositoryRDBImpl
}
