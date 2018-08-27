package net.ryota.app

import akka.http.scaladsl.server.{Directives, StandardRoute}
import net.ryota.serif.domains.ValidationFailureMessage
import net.ryota.validation.Validation.Failures

package object controllers extends Directives {
  def completeEither[V](value: Either[Failures[ValidationFailureMessage] , V])(success: V => StandardRoute): StandardRoute = {
    value match {
      case Left(validationFailureMessages) => complete(validationFailureMessages.map(_.key).mkString(","))
      case Right(rightValue) => success(rightValue)
    }
  }
}
