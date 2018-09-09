package net.ryota.serif.domains

import java.time.ZonedDateTime

import net.ryota.serif.domains.Feed.{Describe, Title}
import net.ryota.validation.Validation

final class Feed (
                                   val id: ID[Feed],
                                   val title: Title,
                                   val describe: Describe,
                                   val createdAt: ZonedDateTime
                                 ) extends Entity

object Feed {

  import FeedValidationFailureMessage._

  case class Title(value: String) extends AnyVal

  case class Describe(value: String) extends AnyVal

  def apply(
             title: String,
             describe: String,
           ): DomainValidationResult[Feed] = {
    val titleV =
      Validation
        .ifTextLengthLess(2)(Seq(titleLengthLess3))
        .and(Validation.ifTextLengthOver(30)(Seq(titleLengthOver30)))
        .and(Validation.ifTextMatchedPattern("")(Seq(titleHasInvalidCharacter)))
        .run(title).map(Title.apply)

    val describeV =
      Validation.ifTextLengthOver(300)(Seq(describeLengthLess3))
          .and(Validation.ifTextLengthLess(2)(Seq(describeLengthOver300)))
          .and(Validation.ifTextMatchedPattern("")(Seq(describeHasInvalidCharacter)))
          .run(describe).map(Describe.apply)

    for {
      title <- titleV
      describe <- describeV
    } yield new Feed(
      id = generateID(),
      title,
      describe,
      createdAt = ZonedDateTime.now()
    )
  }
}


object FeedValidationFailureMessage {

  val titleLengthLess3 = ValidationFailureMessage("titleLengthLess3")

  val titleLengthOver30 = ValidationFailureMessage("titleLengthOver30")

  val titleHasInvalidCharacter = ValidationFailureMessage("titleHasInvalidCharacter")

  val describeLengthLess3 = ValidationFailureMessage("describeLengthLess3")

  val describeLengthOver300 = ValidationFailureMessage("describeLengthOver300")

  val describeHasInvalidCharacter = ValidationFailureMessage("describeHasInvalidCharacter")

}


trait FeedRepository[M[_]] extends Repository[Feed, M] {
  def findAll(): M[Seq[Feed]]
}

trait UsesFeedRepository[F[_]] {
  val feedRepository: FeedRepository[F]
}