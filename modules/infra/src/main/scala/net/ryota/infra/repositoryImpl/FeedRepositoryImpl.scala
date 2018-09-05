package net.ryota.infra.repositoryImpl

import net.ryota.monad.Async
import net.ryota.serif.domains
import net.ryota.serif.domains.{Feed, FeedRepository, ID, UsesFeedRepository}

object FeedRepositoryOnMemory extends FeedRepository[Async] {
  private var feedMap: Map[ID[Feed], Feed] = Map.empty
  override def delete(id: domains.ID[Feed]): Async[Unit] = Async { feedMap = feedMap - id }

  override def findById(id: domains.ID[Feed]) = Async { feedMap.get(id) }

  override def findByIds(ids: Seq[domains.ID[Feed]]): Async[Seq[Feed]] = Async {
    ids.flatMap(id => feedMap.filterKeys(_ == id).values)
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
