package net.ryota.infra.repositoryImpl

import net.ryota.monad.Async
import net.ryota.serif.domains
import net.ryota.serif.domains.{Feed, FeedRepository, ID}

object FeedRepositoryOnMemory extends FeedRepository[Async] {
  private var feedMap: Map[ID[Feed], Feed] = Map.empty
  override def delete(id: domains.ID[Feed]): Async[Unit] = Async { feedMap = feedMap - id }

  override def findById(id: domains.ID[Feed]) = Async { feedMap.get(id) }

  override def findByIds(ids: Seq[domains.ID[Feed]]): Async[Seq[Feed]] = Async {
    ids.flatMap(id => feedMap.filterKeys(_ == id).values)
  }

  override def store(e: Feed): Async[Unit] = Async {
    feedMap = feedMap + (e.id -> e)
  }
}

trait MixInFeedRepository {
  val feedRepository: FeedRepository[_] = FeedRepositoryOnMemory
}
