package net.ryota.app

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, StandardRoute}
import net.ryota.app.controllers.{FeedListResponseJson, FeedResponseJson}
import net.ryota.serif.domains.ValidationFailureMessage
import net.ryota.validation.Validation.Failures
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

package object controllers extends Directives {
  def completeEither[V](value: Either[Failures[ValidationFailureMessage] , V])(success: V => StandardRoute): StandardRoute = {
    println(value)
    value match {
      case Left(validationFailureMessages) => complete(validationFailureMessages.map(_.key).mkString(","))
      case Right(rightValue) => success(rightValue)
    }
  }
}

trait ResponseJSONSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val FeedResponseJsonFormat: RootJsonFormat[FeedResponseJson] = jsonFormat3(FeedResponseJson.apply)
  implicit val FeedListResponseJsonFormat: RootJsonFormat[FeedListResponseJson] = jsonFormat2(FeedListResponseJson)
}