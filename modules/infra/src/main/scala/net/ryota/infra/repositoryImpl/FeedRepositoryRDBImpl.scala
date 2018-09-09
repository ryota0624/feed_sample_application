package net.ryota.infra.repositoryImpl

import net.ryota.infra.Tables
import net.ryota.monad.Async
import net.ryota.serif.domains
import net.ryota.serif.domains.{Feed, FeedRepository, ID, UsesFeedRepository}

object FeedRepositoryRDBImpl extends FeedRepository[Async] {
  private var feedMap: Map[ID[Feed], Feed] = Map.empty
  override def delete(id: domains.ID[Feed]): Async[Unit] = Async { feedMap = feedMap - id }

  override def findById(id: domains.ID[Feed]) = Async {
    Tables.Feeds.filter(_.id == id.value)
  }

  override def findByIds(ids: Seq[domains.ID[Feed]]): Async[Seq[Feed]] = Async {
    Tables.Feeds.filter(_.id in ids.map(_.value)).result
  }

  override def store(e: Feed): Async[Unit] = Async {
    println("call FeedRepositoryOnMemory")

    feedMap = feedMap + (e.id -> e)

    println(feedMap.size)
  }

  def findAll(): Async[Seq[Feed]] = Async {
    feedMap.values.toSeq
  }

}

trait MixInFeedRepository extends UsesFeedRepository[Async] {
  val feedRepository: FeedRepository[Async] = FeedRepositoryOnMemory
}
