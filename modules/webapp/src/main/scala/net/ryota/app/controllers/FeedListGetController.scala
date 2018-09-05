package net.ryota.app.controllers

import akka.http.scaladsl.server.{Directives, Route}
import net.ryota.app.ResponseJSONSupport
import net.ryota.app.usecases.MixInAsyncFeedGetListUsecase
import net.ryota.serif.domains.Feed

import scala.concurrent.ExecutionContext

object FeedListGetController extends Directives with MixInAsyncFeedGetListUsecase with ResponseJSONSupport {

  implicit val context: ExecutionContext = ExecutionContext.global
  val route: Route = (get & pathPrefix("list") &parameterMap) { params =>
    val ids = params.get("ids").map(_.split(',').toSeq)
    val reponseJson = feedGetListUsecase.call(ids.getOrElse(Nil))
        .map(_.map(FeedResponseJson.apply))
        .map(FeedListResponseJson.apply(ids, _)).run
    complete(reponseJson)
  }
}

case class FeedListResponseJson(ids: Option[Seq[String]], feeds: Seq[FeedResponseJson])
case class FeedResponseJson(title: String, describe: String, createdAt: Long)
object FeedResponseJson {
  def apply(f: Feed): FeedResponseJson = new FeedResponseJson(f.title.value, f.describe.value, f.createdAt.toEpochSecond)
}