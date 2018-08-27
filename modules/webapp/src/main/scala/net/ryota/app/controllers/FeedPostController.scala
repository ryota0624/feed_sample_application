package net.ryota.app.controllers

import akka.http.scaladsl.server.{Directives, Route}
import net.ryota.app.usecases.MixInAsyncFeedPostUsecase
import net.ryota.monad.Async

import scala.concurrent.ExecutionContext

object FeedPostController extends Directives with MixInAsyncFeedPostUsecase {
  implicit val context: ExecutionContext = ExecutionContext.global
  val route: Route = (post & pathPrefix("post")) {
    entity(as[FeedPostRequestDto]) {
      form => completeEither(feedPostUsecase.call(form.title, form.describe)) { value =>
        complete("OK")
      }
    }
  }
}

case class FeedPostRequestDto(
                             title: String,
                             describe: String
                             )