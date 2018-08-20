package net.ryota.app.usecases

import net.ryota.infra.repositoryImpl.MixInFeedRepository
import net.ryota.monad.Async
import net.ryota.serif.domains.{Feed, FeedRepository, ID, UsesFeedRepository}

trait FeedGetListUsecase[F[_]] extends UsesFeedRepository[F] {
  def call(ids: Seq[String]) = feedRepository.findByIds(ids.map(ID.apply[Feed]))
}

trait UsesFeedGetListUsecase[F[_]] {
  val feedGetListUsecase: FeedGetListUsecase[F]
}

trait MixInAsyncFeedGetListUsecase extends UsesFeedGetListUsecase[Async]  {
  val feedGetListUsecase: FeedGetListUsecase[Async] = new FeedGetListUsecase[Async] with MixInFeedRepository
}