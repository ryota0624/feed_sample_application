package net.ryota.app.controllers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import net.ryota.app.usecases.MixInAsyncFeedPostUsecase
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.ExecutionContext

object FeedPostController extends Directives with MixInAsyncFeedPostUsecase with FeedPostRequestJSONDtoSupport {

  implicit val context: ExecutionContext = ExecutionContext.global
  val route: Route = (post & pathPrefix("post")) {
    entity(as[FeedPostRequestJSONDto]) {
      form => completeEither(feedPostUsecase.call(form.title, form.describe)) { async =>
        complete(async.run.map(_ => form))
      }
    }
  }
}

case class FeedPostRequestJSONDto(
                                   title: String,
                                   describe: String
                                 )

trait FeedPostRequestJSONDtoSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val FeedPostRequestJsonDtoFormat: RootJsonFormat[FeedPostRequestJSONDto] = jsonFormat2(FeedPostRequestJSONDto)
}