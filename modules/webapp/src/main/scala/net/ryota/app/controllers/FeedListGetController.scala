package net.ryota.app.controllers

import akka.http.scaladsl.server.{Directives, Route}
import net.ryota.app.usecases.MixInAsyncFeedGetListUsecase

import scala.concurrent.ExecutionContext

object FeedListGetController extends Directives with MixInAsyncFeedGetListUsecase {
  implicit val context: ExecutionContext = ExecutionContext.global
  val route: Route = (get & pathPrefix("list") &parameter('ids)) { ids =>
    val f = feedGetListUsecase.call(ids.split(',')).map { feedSeq =>
      feedSeq.map(feed => feed.title.value).mkString(",")
    }.run
    complete(f)
  }
}
