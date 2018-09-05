package net.ryota.app.usecases


import net.ryota.infra.repositoryImpl.MixInFeedRepository
import net.ryota.monad.Async
import net.ryota.serif.domains.{DomainValidationResult, Feed, UsesFeedRepository, ValidationFailureMessage}
import net.ryota.validation.Validation.Failures

trait FeedPostUsecase[F[_]] extends UsesFeedRepository[F] {
  def call(title: String, describe: String): Either[Failures[ValidationFailureMessage], F[Unit]] = {
    val createFeedResult: DomainValidationResult[Feed] = Feed(title = title, describe = describe)
    createFeedResult.map(feedRepository.store)
  }
}

trait UsesFeedPostUsecase[F[_]] {
  val feedPostUsecase: FeedPostUsecase[F]
}

trait MixInAsyncFeedPostUsecase extends UsesFeedPostUsecase[Async]  {
  val feedPostUsecase: FeedPostUsecase[Async] = new FeedPostUsecase[Async] with MixInFeedRepository
}