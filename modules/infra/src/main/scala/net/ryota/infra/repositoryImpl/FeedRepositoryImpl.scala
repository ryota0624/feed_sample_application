package net.ryota.infra.repositoryImpl

import net.ryota.serif.domains
import net.ryota.serif.domains.{Feed, FeedRepository}

import scala.concurrent.Future

object FeedRepositoryImpl extends FeedRepository[Future[_]] {
  override def delete(id: domains.ID[Feed]): Future[_][Unit] = ???

  override def findById(id: domains.ID[Feed]): Future[_][domains.Entity] = ???

  override def findByIds(ids: Seq[domains.ID[Feed]]): Future[_][Seq[Feed]] = ???

  override def store(e: Feed): Future[_][Unit] = ???
}