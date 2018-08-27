package net.ryota.app.usecases


import net.ryota.infra.repositoryImpl.MixInFeedRepository
import net.ryota.monad.Async
import net.ryota.serif.domains.{Feed, ID, UsesFeedRepository}

trait FeedPostUsecase[F[_]] extends UsesFeedRepository[F] {
  def call(title: String, describe: String) = {
    val createFeedResult = Feed(title = title, describe = describe)
    createFeedResult.map(feedRepository.store)
  }
}

trait UsesFeedPostUsecase[F[_]] {
  val feedPostUsecase: FeedPostUsecase[F]
}

trait MixInAsyncFeedPostUsecase extends UsesFeedPostUsecase[Async]  {
  val feedPostUsecase: FeedPostUsecase[Async] = new FeedPostUsecase[Async] with MixInFeedRepository
}